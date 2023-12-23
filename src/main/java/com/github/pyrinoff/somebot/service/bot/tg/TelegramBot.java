package com.github.pyrinoff.somebot.service.bot.tg;

import com.github.pyrinoff.somebot.abstraction.AbstractBot;
import com.github.pyrinoff.somebot.service.PropertyService;
import com.github.pyrinoff.somebot.service.bot.tg.api.ITgMessageProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMediaGroup;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.media.InputMedia;
import org.telegram.telegrambots.meta.api.objects.media.InputMediaPhoto;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot implements AbstractBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired PropertyService propertyService;

    @Autowired
    ITgMessageProcessingService processingService;

    @Override
    public String getBotUsername() {
        return propertyService.getTgBotname();
    }

    @Override
    public String getBotToken() {
        return propertyService.getTgToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        processingService.processUpdate(update);
    }

    public void sendMessage(Long chatId, String textToSend, Boolean protect) {
        sendMessage(chatId, textToSend, protect, null);
    }

    public void sendMessage(Long chatId, String textToSend, Boolean protect, Integer replyToMessageId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setProtectContent(protect);
        if(replyToMessageId != null) sendMessage.setReplyToMessageId(replyToMessageId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Cant sendMessage!");
        }
    }

    public void forwardMessage(Update update, Long chatId, Boolean protect) {
        final ForwardMessage forwardMessage = new ForwardMessage();
        forwardMessage.setChatId(chatId); //forward to that user/group
        forwardMessage.setFromChatId(update.getMessage().getChatId()); //from: chat id
        forwardMessage.setMessageId(update.getMessage().getMessageId()); //from: message id
        forwardMessage.setProtectContent(protect);
        try {
            execute(forwardMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Cant forwardMessage!");
        }
    }

    public void copyMessage(Update update, Long chatId, Boolean protect) {
        copyMessage(update.getMessage().getChatId(), update.getMessage().getMessageId(), chatId, protect);
    }

    public void copyMessage(final Long fromChatId, final Integer fromMessageId, final Long toChatId, Boolean protect) {
        final CopyMessage copyMessage = new CopyMessage();
        copyMessage.setChatId(toChatId); //forward to that user/group
        copyMessage.setFromChatId(fromChatId); //from: chat id
        copyMessage.setMessageId(fromMessageId); //from: message id
        copyMessage.setProtectContent(protect);
        try {
            execute(copyMessage);
        } catch (TelegramApiException e) {
            e.printStackTrace();
            logger.error("Cant copyMessage!");
        }
    }

    public void sendMessageBack(Update update, String text, Boolean protect) {
        sendMessage(update.getMessage().getChatId(), text, protect);
    }

    public void sendPhotoBack(final Update update, final String filepath, final String caption, final Boolean protect, final boolean fromClasspath) {
        final String chatId = String.valueOf(update.getMessage().getChatId());
        final SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        if(caption != null) sendPhoto.setCaption(caption);
        try (final InputStream inputStream = fromClasspath ? TelegramBot.class.getClassLoader().getResourceAsStream(filepath) : new FileInputStream(filepath)) {
            sendPhoto.setPhoto(new InputFile().setMedia(inputStream, filepath));
            sendPhoto.setProtectContent(protect);
            try {
                execute(sendPhoto);
            } catch (TelegramApiException e) {
                logger.error("Cant sendPhoto!");
                e.printStackTrace();
            }
        } catch (IOException e) {
            logger.error("IO error during sendPhotoBack");
            e.printStackTrace();
        }
    }

    public void sendPhotosBack(final Update update, final List<String> files, final String caption, final Boolean protect, final boolean fromClasspath) {
        if(files.size() < 1 || files.size() > 10) throw new IllegalArgumentException();
        if(files.size() == 1) {
            sendPhotoBack(update, files.get(0), caption, protect, fromClasspath);
            return;
        }
        final String chatId = String.valueOf(update.getMessage().getChatId());
        final ArrayList<InputMedia> inputMedias =  new ArrayList<>(10);
        for (final String oneFilepath : files) {
            InputMediaPhoto inputMediaPhoto = new InputMediaPhoto();
            if(caption != null) inputMediaPhoto.setCaption(caption);
            //try (final InputStream inputStream = fromClasspath ? TelegramBot.class.getClassLoader().getResourceAsStream(oneFilepath) : new FileInputStream(oneFilepath)) {
            try {
                final InputStream inputStream = fromClasspath ? TelegramBot.class.getClassLoader().getResourceAsStream(oneFilepath) : new FileInputStream(oneFilepath);
                inputMediaPhoto.setMedia(inputStream, oneFilepath);
                inputMedias.add(inputMediaPhoto);
            }
            catch (IOException e) {
                logger.error("Cant setMedia, IOException!");
                e.printStackTrace();
                return;
            }
        }
        final SendMediaGroup sendMediaGroup = new SendMediaGroup();
        sendMediaGroup.setChatId(chatId);
        sendMediaGroup.setMedias(inputMedias);
        sendMediaGroup.setProtectContent(protect);
        try {
            execute(sendMediaGroup);
        } catch (TelegramApiException e) {
            logger.error("Cant SendMediaGroup (photos)!");
            e.printStackTrace();
        }
    }

    @Override
    public void initialize() {
        if(!propertyService.getTgEnabled()) {
            logger.info("Telegram bot: disabled");
            return;
        }
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
        logger.info("Initialized bot: tg, name: " + getBotUsername());
    }

}
