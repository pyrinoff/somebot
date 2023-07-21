package ru.pyrinoff.somebotexamples.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.pyrinoff.somebotexamples.example.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>  {

}