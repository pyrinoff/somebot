package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.model.User;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface IUserService<U extends User> {

    U createUser(Long chatId);

    U getUser(Long chatId);

    U saveUser(@Nullable U user);

    U addOrUpdate(@NotNull final U user);

}
