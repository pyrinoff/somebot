package ru.pyrinoff.somebotexamples.example.service;

import jakarta.transaction.Transactional;
import lombok.Getter;
import lombok.SneakyThrows;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.pyrinoff.somebotexamples.example.exception.model.UserNullException;
import ru.pyrinoff.somebotexamples.example.api.service.IUserService;
import ru.pyrinoff.somebotexamples.example.exception.model.IdNullException;
import ru.pyrinoff.somebotexamples.example.model.User;
import ru.pyrinoff.somebotexamples.example.repository.UserRepository;

@Service
public class UserService implements IUserService {

    public static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Getter
    @Autowired
    private UserRepository userRepository;

    public User createUser(Long chatId) {
        return saveUser((User) new User().setChatId(chatId));
    }

    @NotNull
    @Transactional
    public User addOrUpdate(@NotNull final User user) {
        return getUserRepository().save(user);
    }

    @SneakyThrows
    public User getUser(Long chatId) {
        if(chatId == null) throw new IdNullException();
        return getUserRepository().findById(chatId).orElse(null);
    }

    @SneakyThrows
    public User saveUser(@Nullable User user) {
        if(user == null) throw new UserNullException();
        if(user.getChatId() == null) throw new IdNullException();
        return addOrUpdate(user);
    }

}
