package com.github.pyrinoff.somebot.service.bot.vk;

import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.GroupActor;
import com.vk.api.sdk.events.longpoll.GroupLongPollApi;
import com.vk.api.sdk.objects.callback.MessageObject;
import com.vk.api.sdk.objects.callback.VkPayTransaction;
import com.vk.api.sdk.objects.messages.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VkBotHandler extends GroupLongPollApi {

    public static final Logger logger = LoggerFactory.getLogger(VkBotHandler.class);

    VkBot vkBot;

    public VkBotHandler(VkApiClient client, GroupActor actor, int waitTime, VkBot vkBot) {
        super(client, actor, waitTime);
        this.vkBot = vkBot;
    }

    @Override
    protected void messageNew(Integer groupId, MessageObject update) {
        logger.debug(update.getMessage().toPrettyString());
        vkBot.onUpdateReceived(update); //forward to VkBot > ProcessingService
    }

    @Override
    protected void vkPayTransaction(Integer groupId, VkPayTransaction message) {
        logger.debug(message.toString());
        vkBot.processVkPayTransaction(message); //forward to VkBot > ProcessingService
    }

    /*        MessageFromBot messageFromBot = null;
        try {
            messageFromBot = convertOriginalMessage(message);
        }
        catch (Exception e) {
            logger.debug("convertOriginalMessage exc:"+e);
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            logger.debug("convertOriginalMessage exc trace:"+sw.toString());
            return;
        }

        if (messageFromBot == null) {
            logger.debug("messageFromBot (after convertOriginalMessage) is null");
            return;
        }
        mainBotThread.addMessage(messageFromBot);*/

/*    public static MessageFromBot convertOriginalMessage(Message originalMessage) {
        return convertOriginalMessage(originalMessage, 0);
    }

    public static MessageFromBot convertOriginalMessage(Message originalMessage, int maxDepth) {
        MessageFromBot messageFromBot = null;

        if (originalMessage.getPayload() != null) {
            org.apache.log4j.Logger.getRootLogger().debug("Payload (raw): " + originalMessage.getPayload());
            String payloadString = null;
            try {
                payloadString = new Gson().fromJson(originalMessage.getPayload(), String.class);
            }
            catch (IllegalStateException | JsonSyntaxException e) {
                org.apache.log4j.Logger.getRootLogger().debug("Seems like payload is not a string!");
                payloadString = originalMessage.getPayload();
            }

            messageFromBot = new MessageWithPayload(
                    botType,
                    originalMessage,
                    originalMessage.getId(),
                    originalMessage.getFromId().toString(),
                    originalMessage.getPeerId().toString(),
                    payloadString,
                    originalMessage.getText()
            );
        }else if (originalMessage.getText() != null || (originalMessage.getAttachments()!=null && originalMessage.getAttachments().size() > 0)) {
            logger.debug("No payload message!");
            List<MessageAttachment> messageAttachments = null;
            if (maxDepth == 0 || originalMessage.getFwdMessages().size()==0) {
                messageFromBot = new Simple(botType
                        , originalMessage
                        , originalMessage.getId()
                        , originalMessage.getFromId().toString()
                        , originalMessage.getPeerId().toString()
                        , originalMessage.getText()
                );
                messageAttachments = originalMessage.getAttachments();
            }
            else {
                //Получаем самое вложенное сообщение (но не глубже maxDepth)
                int currentDepth=1;
                ForeignMessage foreignMessage = originalMessage.getFwdMessages().get(0);
                while(currentDepth<maxDepth && foreignMessage.getFwdMessages()!=null && foreignMessage.getFwdMessages().size()>0) {
                    foreignMessage = foreignMessage.getFwdMessages().get(0);
                    currentDepth++;
                }
                messageFromBot = new Simple(botType
                        , originalMessage
                        , originalMessage.getId()
                        , originalMessage.getFromId().toString()
                        , originalMessage.getPeerId().toString()
                        , foreignMessage.getText()
                );
                messageAttachments = foreignMessage.getAttachments();

            }

            for (MessageAttachment oneOriginalAttachment : messageAttachments) {
                if (oneOriginalAttachment.getType() == MessageAttachmentType.PHOTO) {

                    String url = null;

                    //Пытаемся определить наибольшее изображение по width. Если не выходит - берем последнее (оно, по идее, самое большое)
                    //List<Image> imageList = oneOriginalAttachment.getPhoto().getImages();
                    List<PhotoSizes> imageList = oneOriginalAttachment.getPhoto().getSizes();
                    if (imageList == null || imageList.size() < 1) {
                        Logger.getRootLogger().error("Got MessageAttachmentType.PHOTO, but no imageList in it...");
                        continue;
                    }
                    //Optional<Image> maxSizeImage = imageList.stream().max(Comparator.comparing(Image::getWidth));
                    Optional<PhotoSizes> maxSizeImage = imageList.stream().max(Comparator.comparing(PhotoSizes::getWidth));
                    url = maxSizeImage.isPresent() ? maxSizeImage.get().getUrl().toString() : imageList.get(imageList.size() - 1).getUrl().toString();


                    try {
                        messageFromBot.addAttachment(new PhotoFromBot(url));
                    } catch (MalformedURLException e) {
                        e.printStackTrace();
                    }
                }
            }


            *//*
            System.out.println("--------------------");
            messageFromBot.getAttachments().forEach( att -> {
                System.out.println(((PhotoFromBot)att).getUrl());
            });*//*
        }
        else {
            logger.debug("Unknown message!");
            System.out.println(originalMessage);
        }

        return messageFromBot;
    }*/


}