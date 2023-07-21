package ru.pyrinoff.somebot.service;

import jakarta.transaction.Transactional;
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
import ru.pyrinoff.somebot.model.AbstractUser;
import ru.pyrinoff.somebot.model.Message;
import ru.pyrinoff.somebot.model.User;
import ru.pyrinoff.somebot.repository.UserRepository;

import java.io.*;
import java.util.Optional;

@Service
public abstract class AbstractUserService<M extends AbstractUser> implements IUserService<M> {

    public static final Logger logger = LoggerFactory.getLogger(AbstractUserService.class);

    @NotNull
    @Transactional
    public M addOrUpdate(@NotNull final M user) {
        return getUserRepository().save(user);
    }

    @SneakyThrows
    @Override
    public M getUser(Long chatId) {
        if(chatId == null) throw new IdNullException();
        return getUserRepository().findById(chatId).orElse(null);
    }

    @SneakyThrows
    @Override
    public M saveUser(@Nullable M user) {
        if(user == null) throw new UserNullException();
        if(user.getChatId() == null) throw new IdNullException();
        return addOrUpdate(user);
    }

  /*  @Override
    public M createUser(Long chatId) {
        return saveUser(new User().setChatId(chatId));
    }*/


}
