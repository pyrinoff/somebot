package ru.pyrinoff.somebotexamples.example.api.service;

import org.jetbrains.annotations.Nullable;
import ru.pyrinoff.somebotexamples.example.model.User;

public interface IUserService {

    User createUser(Long chatId);

    User getUser(Long chatId);

    User saveUser(@Nullable User user);

}
