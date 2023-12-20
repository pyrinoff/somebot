package com.github.pyrinoff.somebot.service.bot.vk;

import com.github.pyrinoff.somebot.abstraction.AbstractBot;
import com.github.pyrinoff.somebot.service.PropertyService;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class VkBot implements AbstractBot {

    private static final Logger logger = LoggerFactory.getLogger(VkBot.class);

    @Autowired
    PropertyService propertyService;

    @Autowired VkMessageProcessingService processingService;
    private GroupActor groupActor;
    private VkApiClient vkApiClient;
    private VkBotHandler handler;
    private Random randomUtil;

    public Integer getGroupId() {
        return propertyService.getVkGroupId();
    }

    public String getGroupToken() {
        return propertyService.getVkGroupToken();
    }

    public String getAdminToken() {
        return propertyService.getVkAdminToken();
    }

    public String getAdminId() {
        return propertyService.getVkAdminId();
    }

    public void onUpdateReceived(MessageObject update) {
        processingService.processUpdate(update);
    }



    public void sendMessage(Integer chatId, String textToSend, Boolean protect) {
        sendMessage(chatId, textToSend, protect, null);
    }

    public void sendMessage(Integer chatId, String textToSend, Boolean protect, Integer replyToMessageId) {
/*        if (messageToBot.getText() == null && !messageToBot.hasAttachments()) {
            logger.error("No text and no attachments! Skip the message");
            return;
        }*/

        VkApiClient vkApiClient = new VkApiClient(new HttpTransportClient());
        MessagesSendQuery messagesSendQuery = vkApiClient.messages().send(groupActor).randomId(randomUtil.nextInt());

        //Set destination ID
//        messagesSendQuery.peerId(Integer.valueOf(messageToBot.getRecipientNativeId()));
        messagesSendQuery.peerId(chatId);

        //Text
        if(textToSend != null) messagesSendQuery.message(textToSend);

        /*
        //menu data
        if (messageToBot.hasMenu()) {
            List<List<KeyboardButton>> allKeyboardButtons = new ArrayList<>();
            ArrayList<ArrayList<MenuButton>> buttonLines = messageToBot.getMenu().getMenuButtons(); //from msg
            for (ArrayList<MenuButton> oneLine : buttonLines) {
                //инициализируем линию кнопок
                List<KeyboardButton> keyboardRow = new ArrayList<>();
                for (MenuButton oneButton : oneLine) {
                    //инициализируем action клавиши
                    KeyboardButtonAction keyboardButtonAction = new KeyboardButtonAction();
                    //задаем ее параметры
                    keyboardButtonAction.setLabel(oneButton.getText()); //текст
                    if (oneButton.getPayload() != null) {
                        //payload, максимальная длина 1000, данные в формате JSON
                        keyboardButtonAction.setPayload(new Gson().toJson(oneButton.getPayload()).toString());
                        keyboardButtonAction.setType(TemplateActionTypeNames.TEXT);
                    } else keyboardButtonAction.setType(TemplateActionTypeNames.TEXT);

                    //инициализируем кнопку с созданным action
                    KeyboardButton keyboardButton = new KeyboardButton().setAction(keyboardButtonAction);
                    //добавляем в линию
                    keyboardRow.add(keyboardButton);
                }
                //добавляем линию кнопок в общий лист
                allKeyboardButtons.add(keyboardRow);
            }


            if (allKeyboardButtons.size() > 6 && messageToBot.isInline())
                logger.error("Too big size for inline menu: " + allKeyboardButtons.size() + ", max is 6 (by 05.11.2021)");


            Keyboard keyboard = new Keyboard();
            keyboard.setButtons(allKeyboardButtons);
            keyboard.setOneTime(messageToBot.isOneTime());
            keyboard.setInline(messageToBot.isInline());
            messagesSendQuery.keyboard(keyboard);
        } else if (messageToBot.isNeedToClearMenu()) {
            Keyboard keyboard = new Keyboard();
            keyboard.setInline(false);
            keyboard.setButtons(new ArrayList<>());
            messagesSendQuery.keyboard(keyboard);
        }
         */
/*
        //attachments
        if (messageToBot.hasAttachments()) {
            ArrayList<String> attachmentsStrings = new ArrayList<>();

            //Photos
            List<String> photoPaths = new ArrayList<>();
            List<Photo> photos = messageToBot.getAttachmentsPhotos();
            for (Photo oneAttachmentPhoto : photos) photoPaths.add(oneAttachmentPhoto.getLocalFilepath());


            if (photos.size() > 0) {
                ArrayList<String> photosStrings = PhotosUpload.go(groupActor, logger, photoPaths);
                attachmentsStrings.addAll(photosStrings);
            }

            //add all attachments
            System.out.println("!!!___!!!");
            System.out.println(String.join(",", attachmentsStrings));
            messagesSendQuery.attachment(String.join(",", attachmentsStrings));
        }
*/
        /*
        //forward pyrinoff.somebot.messages
        if (messageToBot.hasForwardMessages()) {
            messagesSendQuery.forwardMessages(messageToBot.getForwardMessages());
        }
         */

        //Send
        try {
            messagesSendQuery.execute();
        } catch (ApiException e) {
            logger.error("INVALID REQUEST", e);
        } catch (ClientException e) {
            logger.error("NETWORK ERROR", e);
        }

    }

/*

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
*/

    public void sendMessageBack(VkMessage message, String text, Boolean protect) {
        sendMessage(message.getOriginalMessage().getMessage().getPeerId(), text, protect);
    }

/*
    public void sendPhotoBack(final Update update, final String filepath, final String caption, final Boolean protect, final boolean fromClasspath) {
        final String chatId = String.valueOf(update.getMessage().getChatId());
        final SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId);
        if(caption != null) sendPhoto.setCaption(caption);
        try (final InputStream inputStream = fromClasspath ? VkBot.class.getClassLoader().getResourceAsStream(filepath) : new FileInputStream(filepath)) {
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
                final InputStream inputStream = fromClasspath ? VkBot.class.getClassLoader().getResourceAsStream(oneFilepath) : new FileInputStream(oneFilepath);
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
*/

    @Override
    public void initialize() {
        if(!propertyService.getVkEnabled()) {
            logger.info("Telegram bot: disabled");
            return;
        }

        vkApiClient = new VkApiClient(new HttpTransportClient());
        groupActor = new GroupActor(getGroupId(), getGroupToken());
        handler = new VkBotHandler(vkApiClient, groupActor, 100, this);
        randomUtil = new Random();

        try {
            logger.error("Initialized bot: vk, link: https://vk.com/im?sel=-" + getGroupId());
            handler.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
