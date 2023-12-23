package com.github.pyrinoff.somebot.repository;

import com.github.pyrinoff.somebot.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository<U extends User> extends JpaRepository<U, Long>  {

}