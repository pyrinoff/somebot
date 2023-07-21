package ru.pyrinoff.somebot.service.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.CopyMessage;
import org.telegram.telegrambots.meta.api.methods.ForwardMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.pyrinoff.somebot.api.service.IMessageProcessingService;
import ru.pyrinoff.somebot.service.PropertyService;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    @Autowired PropertyService propertyService;

    @Autowired IMessageProcessingService processingService;

    @Override
    public String getBotUsername() {
        return propertyService.getBotname();
    }

    @Override
    public String getBotToken() {
        return propertyService.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        processingService.process(update);
    }

    public void sendMessage(Long chatId, String textToSend, Boolean protect) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(textToSend);
        sendMessage.setProtectContent(protect);
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
        final CopyMessage copyMessage = new CopyMessage();
        copyMessage.setChatId(chatId); //forward to that user/group
        copyMessage.setFromChatId(update.getMessage().getChatId()); //from: chat id
        copyMessage.setMessageId(update.getMessage().getMessageId()); //from: message id
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

}
