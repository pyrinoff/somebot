package com.github.pyrinoff.somebot.service.bot.vk;

import com.github.pyrinoff.somebot.abstraction.AbstractBot;
import com.github.pyrinoff.somebot.exception.service.CantParseDocDownloadLinkException;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.service.PropertyService;
import com.github.pyrinoff.somebot.service.bot.vk.api.IVkMessageProcessingService;
import com.github.pyrinoff.utils.GetByUrlUtil;
import com.github.pyrinoff.utils.RegexUtil;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.httpclient.HttpTransportClient;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.callback.VkPayTransaction;
import com.vk.api.sdk.objects.docs.Doc;
import com.vk.api.sdk.objects.messages.Keyboard;
import com.vk.api.sdk.objects.messages.Message;
import com.vk.api.sdk.objects.photos.responses.GetWallUploadServerResponse;
import com.vk.api.sdk.objects.photos.responses.PhotoUploadResponse;
import com.vk.api.sdk.objects.photos.responses.SaveWallPhotoResponse;
import com.vk.api.sdk.objects.responses.VideoUploadResponse;
import com.vk.api.sdk.objects.users.responses.GetResponse;
import com.vk.api.sdk.objects.video.responses.SaveResponse;
import com.vk.api.sdk.queries.messages.MessagesGetByIdQuery;
import com.vk.api.sdk.queries.messages.MessagesSendQuery;
import com.vk.api.sdk.queries.photos.PhotosGetWallUploadServerQuery;
import com.vk.api.sdk.queries.photos.PhotosSaveWallPhotoQuery;
import com.vk.api.sdk.queries.upload.UploadPhotoQuery;
import com.vk.api.sdk.queries.users.UsersGetQuery;
import com.vk.api.sdk.queries.video.VideoSaveQuery;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.*;

@Component
public class VkBot implements AbstractBot {

    private static final Logger logger = LoggerFactory.getLogger(VkBot.class);

    public static final String START_BUTTON_PAYLOAD = "{\"command\":\"start\"}";
    public static final String VK_DOMAIN = "https://vk.ru";

    @Autowired
    PropertyService propertyService;

    @Autowired
    IVkMessageProcessingService processingService;

    @Getter
    private GroupActor groupActor;

    @Getter
    private UserActor userActor;

    @Getter
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

    public Integer getAdminId() {
        return propertyService.getVkAdminId();
    }

    public void onUpdateReceived(MessageObject update) {
        processingService.processUpdate(update);
    }

    public void processVkPayTransaction(VkPayTransaction update) {
        processingService.processVkPayTransaction(update);
    }

    public void sendMessageBack(MessageObject message, String text) {
        sendMessage(message.getMessage().getPeerId(), text, null);
    }

    public void sendMessageBack(User user, String text) {
        sendMessage(user.getChatId().intValue(), text, null);
    }

    public void sendMessageBack(MessageObject message, String text, @Nullable Keyboard keyboard) {
        sendMessage(message.getMessage().getPeerId(), text, keyboard);
    }

    public void sendMessageBack(User user, String textToSend, @Nullable Keyboard keyboard
    ) {
        sendMessage(user.getChatId().intValue(), textToSend, keyboard, null, null);
    }

    public void sendMessage(Integer chatId, String textToSend) {
        sendMessage(chatId, textToSend, null, null, null);
    }

    public void sendMessage(Integer chatId, String textToSend, @Nullable Keyboard keyboard) {
        sendMessage(chatId, textToSend, keyboard, null, null);
    }

    public void sendMessage(Integer chatId,
                            String textToSend,
                            @Nullable Keyboard keyboard,
                            @Nullable Integer replyToMessageId,
                            @Nullable Integer[] forwardMessages
    ) {
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
        if (textToSend != null) messagesSendQuery.message(textToSend);
        if (keyboard != null) {
            logger.debug("Keyboard: " + keyboard);
            messagesSendQuery.keyboard(keyboard);
        }
        if (forwardMessages != null) messagesSendQuery.forwardMessages(forwardMessages);

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
            if (e.getCode() == 911) logger.error("Keyboard: " + keyboard);
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

    public List<GetResponse> getUserInfo(@Nullable String idsOrUsernames, @Nullable ArrayList<String> fieldsList) {
        HashMap<String, String> hashMap = new HashMap<>();
//        UsersGetQuery usersGetQuery = null;
//        if (actor instanceof GroupActor) usersGetQuery = vkApiClient.users().get((GroupActor) actor);
//        else usersGetQuery = vkApiClient.users().get((UserActor) actor);
        UsersGetQuery usersGetQuery = vkApiClient.users().get(groupActor);
        if (idsOrUsernames != null) usersGetQuery.userIds(idsOrUsernames);

        if (fieldsList == null) {
            fieldsList = new ArrayList<>();
            fieldsList.add("screen_name");
            fieldsList.add("first_name");
            fieldsList.add("last_name");
        }

        List<GetResponse> responses = new ArrayList<>();
        try {
            responses = usersGetQuery.execute();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }

        if (responses.size() > 1) {
            logger.error("getUserInfo returns " + responses.size() + " responses!!!");
        }
        return responses;
    }

    public GetResponse getUserInfo(@NotNull Integer id, @Nullable ArrayList<String> fieldsList) {
        return getUserInfo(String.valueOf(id), fieldsList).get(0);
    }

    @Override
    public void initialize() {
        if (!propertyService.getVkEnabled()) {
            logger.info("Vk bot: disabled");
            return;
        }

        vkApiClient = new VkApiClient(new HttpTransportClient());
        groupActor = new GroupActor(getGroupId(), getGroupToken());
        userActor = new UserActor(getAdminId(), getAdminToken());
        handler = new VkBotHandler(vkApiClient, groupActor, 100, this);
        randomUtil = new Random();

        try {
            logger.error("Initialized bot: vk, link: " + VK_DOMAIN + "/im?sel=-" + getGroupId());
            handler.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<Message> getMessages(Integer... messageIds) {
        MessagesGetByIdQuery messagesGetByIdQuery = vkApiClient.messages().getById(groupActor, messageIds);
        try {
            return messagesGetByIdQuery.execute().getItems();
        } catch (ApiException | ClientException e) {
            logger.error("Cant getMessages, ex: " + Arrays.toString(e.getStackTrace()));
            return Collections.emptyList();
            //throw new RuntimeException(e);
        }
    }

    public static String getPostUrl(Integer groupId, Integer postId, boolean isGroup) {
        return "https://vk.com/wall" + getPostIdAsString(groupId, postId, isGroup);
    }

    public static String getPostIdAsString(Integer ownerId, Integer postId, boolean isGroup) {
        if (isGroup && ownerId > 0) ownerId = -1 * ownerId;
        return ownerId + "_" + postId;
    }

    public List<String> uploadPhotos(Integer wallId, boolean isGroup, List<String> filepaths) throws ClientException, ApiException {
        if (wallId == null) throw new IllegalArgumentException("uploadPhoto: wallId is null");
        if (wallId < 0) wallId = Math.abs(wallId); //в этом методе только плюсовые
        URI photoUploadServer = null;
        List<String> attachmentsStrings = new ArrayList<>();
        VkApiClient vkApiClient = getNewVkApiClient();
        //vkApiClient.setVersion("5.131");

        for (String oneFilepath : filepaths) {
            if (photoUploadServer == null) {
                PhotosGetWallUploadServerQuery wallUploadServerQuery = vkApiClient.photos().getWallUploadServer(userActor);
                wallUploadServerQuery.groupId(wallId);
                GetWallUploadServerResponse wallUploadServerResponse = wallUploadServerQuery.execute();
                photoUploadServer = wallUploadServerResponse.getUploadUrl();
            }
            synchronized (this) {
                UploadPhotoQuery uploadPhotoQuery = vkApiClient.upload()
                        .photo(photoUploadServer.toString(), new File(((oneFilepath))));
                PhotoUploadResponse photoUploadResponse = uploadPhotoQuery.execute();
                logger.debug("addAttachmentsToWall: photo uploaded, will saveMessagesPhoto");

                PhotosSaveWallPhotoQuery photosSaveWallPhotoQuery = vkApiClient.photos()
                        .saveWallPhoto(userActor, photoUploadResponse.getPhoto())
                        .hash(photoUploadResponse.getHash())
                        .server(photoUploadResponse.getServer());
                logger.info("Photo object: " + photoUploadResponse.toPrettyString());
                if (isGroup) photosSaveWallPhotoQuery.groupId(wallId);
                else photosSaveWallPhotoQuery.userId(wallId);
                List<SaveWallPhotoResponse> saveWallPhotoResponses = photosSaveWallPhotoQuery.execute();

                for (SaveWallPhotoResponse oneSaved : saveWallPhotoResponses) {
                    String attachmentString = "photo" + oneSaved.getOwnerId() + "_" + oneSaved.getId();
                    attachmentsStrings.add(attachmentString);
                    logger.debug("addAttachmentsToWall: photo saveWallPhoto: " + attachmentString);
                }
            }
        }
        if (attachmentsStrings.size() < 1) return null;
        return attachmentsStrings;
    }

    public static String getVkMentionString(Integer userId, String textString) {
        return "[id" + userId + "|" + textString + "]";
    }

    private static Integer getCorrectOwnerId(Integer ownerId, boolean isGroup) {
        if (isGroup && ownerId > 0 || !isGroup && ownerId < 0) return -1 * ownerId;
        return ownerId;
    }

    public String getVkMentionStringWithName(Integer id) {
        GetResponse userInfo = getUserInfo(id, null);
        String mentionString = userInfo.getFirstName() + " " + userInfo.getLastName();
        return getVkMentionString(id, mentionString);
    }

    public static String getPmInGroupUrl(Integer groupId, Integer userId) {
        return VK_DOMAIN + "/gim" + groupId + "?sel=" + userId;
    }

    public static String getIdString(Integer userId) {
        return VK_DOMAIN + "/id" + userId;
    }

    private VkApiClient getNewVkApiClient() {
        return new VkApiClient(new HttpTransportClient());
    }


    public String uploadVideo(Integer wallId, String filepath, @Nullable String videoName, @Nullable String description) {
        if (wallId == null) throw new IllegalArgumentException("uploadVideo: wallId is null");
        wallId = Math.abs(wallId); //в этом методе только плюсовые
        VkApiClient vkApiClient = getNewVkApiClient();
        //vkApiClient.setVersion("5.199");

        VideoSaveQuery videoSaveQuery = vkApiClient.videos().save(userActor);
        videoSaveQuery.groupId(wallId);
        videoSaveQuery.isPrivate(false);
        if (videoName != null) videoSaveQuery.name(videoName);
        if (description != null) videoSaveQuery.description(description);
        SaveResponse saveResponse = null;
        try {
            saveResponse = videoSaveQuery.execute();
        } catch (ApiException |
                 ClientException e) { //выглядит не оч красиво, но за счет RuntimeException можем юзать стримы в будущем
            throw new RuntimeException(e);
        }
        try {
            VideoUploadResponse videoUploadResponse = vkApiClient
                    .upload().video(
                            saveResponse.getUploadUrl().toString(),
                            new File(filepath)
                    ).execute();
        } catch (ApiException | ClientException e) {
            throw new RuntimeException(e);
        }
        return "video" + saveResponse.getOwnerId() + "_" + saveResponse.getVideoId();
    }

    public static String getDirectUrl(Doc doc) throws IOException {
        String contentOfVkPageWithLink = GetByUrlUtil.getContentFromURL(doc.getUrl().toString());
        List<String> firstFound = RegexUtil.getFirstFound(contentOfVkPageWithLink, "\"(https:\\/\\/[^\"]*?\\.userapi\\.com[^\"]*?)\\?[^\"]*?dl=1\"");
        if (firstFound != null && firstFound.size() == 1) return (String) firstFound.get(0);

        logger.error("CAbt get direct url for doc: " + doc);
        logger.error("Content from url: " + contentOfVkPageWithLink.substring(0, 2048)); //if binary - less info
        throw new CantParseDocDownloadLinkException();
    }

}
