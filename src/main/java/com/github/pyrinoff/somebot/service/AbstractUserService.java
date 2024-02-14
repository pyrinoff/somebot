package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.exception.model.IdNullException;
import com.github.pyrinoff.somebot.exception.model.UserNullException;
import com.github.pyrinoff.somebot.model.User;
import com.github.pyrinoff.somebot.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.ParameterizedType;


@Service
public class AbstractUserService<U extends User> implements IUserService<U> {

    public static final Logger logger = LoggerFactory.getLogger(AbstractUserService.class);

    @Getter
    @Autowired
    private UserRepository<U> userRepository;

    public U createUser(Long chatId) {
        U user = createUserFromGeneric();
        user.setChatId(chatId);
        //user.setCreatedDate(new Date());
        return saveUser(user);
    }

    public U createUserFromGeneric() {
        ParameterizedType superClass = (ParameterizedType) getClass().getGenericSuperclass();
        Class<U> type = (Class<U>) superClass.getActualTypeArguments()[0];
        try {
            U t = (U) type.getDeclaredConstructor().newInstance();
            return t;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @NotNull
    @Transactional
    public U addOrUpdate(@NotNull final U user) {
        return userRepository.save(user);
    }

    @SneakyThrows
    public U getUser(Long chatId) {
        if(chatId == null) throw new IdNullException();
        return userRepository.findById(chatId).orElse(null);
    }

    @SneakyThrows
    @Transactional
    public U saveUser(@Nullable U user) {
        if(user == null) throw new UserNullException();
        if(user.getChatId() == null) throw new IdNullException();
        return addOrUpdate(user);
    }

    @Transactional
    public U changeUserBalance(U user, double amount) {
        user.setBalance(user.getBalance() + amount);
        return saveUser(user);
    }

}
