package ru.pyrinoff.somebot.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pyrinoff.somebot.api.service.IUserService;
import ru.pyrinoff.somebot.exception.model.IdNullException;
import ru.pyrinoff.somebot.exception.model.UserNullException;
import ru.pyrinoff.somebot.model.Message;
import ru.pyrinoff.somebot.model.User;
import ru.pyrinoff.somebot.repository.UserRepository;

import java.io.*;

@Service
public class UserService extends AbstractUserService<User> {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Getter
    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(Long chatId) {
        return saveUser((User) new User().setChatId(chatId));
    }

    public void updateUserInMessage(Message message) {
        //Check if setup is possible
        if (!message.getOriginalMessage().hasMessage()) return;

        //Get user from DB
        Long chatId = message.getOriginalMessage().getMessage().getChatId();
        User user = getUser(chatId);

        //If user not in DB - register him
        if (user == null) {
            user = createUser(chatId);
            if (user == null) {
                logger.error("Cant registerUser!");
                return;
            }
            message.setFirstMessageFromUser(true);
        }
        //Set found user in message
        message.setUser(user);
    }

}
