package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.api.command.ICommandWithTimestampAndChatId;
import com.github.pyrinoff.somebot.api.service.IAntiFloodService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class AntiFloodService implements IAntiFloodService {

    private static int MAX_MESSAGES = 2;  // Maximum number of messages allowed
    private static long TIME_INTERVAL = 1;  // Time interval in seconds
    private static final int CLEANUP_EVERY_X_MESSAGES = 10;  // Time interval in milliseconds (e.g., 10 seconds)
    private static int CLEANUP_COUNTER = 0;  // Time interval in milliseconds (e.g., 10 seconds)
    private final Map<Long, Integer> userLastMessageTime = new HashMap<>();
    private final Map<Long, Integer> userMessageCount  = new HashMap<>();

    public static void setMaxMessages(int maxMessages) {
        MAX_MESSAGES = maxMessages;
    }

    public static void setTimeInterval(long timeInterval) {
        TIME_INTERVAL = timeInterval;
    }

    @Override
    public boolean isFloodMessage(ICommandWithTimestampAndChatId message) {
        final Integer messageTimestamp = message.getMessageTimestamp();
        final Long userId = message.getSenderChatId();
        if (userLastMessageTime.containsKey(userId)) {
            if (messageTimestamp - userLastMessageTime.get(userId) < TIME_INTERVAL) {
                userMessageCount.put(userId, userMessageCount.getOrDefault(userId, 0) + 1);
                if(userMessageCount.getOrDefault(userId, 0) >= MAX_MESSAGES) {
                    userLastMessageTime.put(userId, messageTimestamp);
                    return true;
                }
            }
            else userMessageCount.put(userId, 0);
        }
        userLastMessageTime.put(userId, messageTimestamp);
        if(CLEANUP_COUNTER++ > CLEANUP_EVERY_X_MESSAGES) cleanUpOldEntries();
        return false;
    }

    private void cleanUpOldEntries() {
        CLEANUP_COUNTER = 0;
        long currentTime = System.currentTimeMillis();
        if(!userLastMessageTime.isEmpty()) userLastMessageTime.entrySet().removeIf(entry -> currentTime - entry.getValue() > TIME_INTERVAL);
        if(!userMessageCount.isEmpty()) userMessageCount.entrySet().removeIf(entry -> currentTime - userLastMessageTime.get(entry.getKey()) > TIME_INTERVAL);
    }

}
