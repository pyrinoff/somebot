package com.github.pyrinoff.somebot.service;

import com.github.pyrinoff.somebot.model.User;
import org.springframework.stereotype.Service;

@Service
public class UserService extends AbstractUserService<User> implements IUserService<User> {

}
