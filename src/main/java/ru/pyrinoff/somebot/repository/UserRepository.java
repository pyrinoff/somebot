package ru.pyrinoff.somebot.repository;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pyrinoff.somebot.model.User;

@Repository
public interface UserRepository extends AbstractUserRepository<User> {

}
