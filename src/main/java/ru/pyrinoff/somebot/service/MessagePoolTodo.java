package ru.pyrinoff.somebot.service;

import org.telegram.telegrambots.meta.api.objects.Update;
import ru.pyrinoff.somebot.api.component.IMessagePool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessagePoolTodo implements IMessagePool {

    private BlockingQueue<Update> messagesQueue;

    public MessagePoolTodo() {
        messagesQueue = new LinkedBlockingQueue<>();
    }

    public void addMessageToProcessingList(Update message) {
        boolean offerComplete = messagesQueue.offer(message);
        if(!offerComplete) return;
    }

    public Update readAndProcessMessage() {
        try {
            return messagesQueue.take();
        } catch (InterruptedException e) {
            return null;
        }
    }

    public boolean isEmpty() {
        return messagesQueue.isEmpty();
    }

    public int size() {
        return messagesQueue.size();
    }

}
