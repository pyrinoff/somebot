package ru.pyrinoff.somebot.api.service;

import ru.pyrinoff.somebot.model.AbstractUser;
import ru.pyrinoff.somebot.model.Message;
import ru.pyrinoff.somebot.model.User;
import ru.pyrinoff.somebot.repository.AbstractUserRepository;

public interface IUserService<M extends AbstractUser> {

    M getUser(Long chatId);

    M saveUser(M user);

    M createUser(Long chatId);

    void updateUserInMessage(Message message);

    AbstractUserRepository<M> getUserRepository();

}
