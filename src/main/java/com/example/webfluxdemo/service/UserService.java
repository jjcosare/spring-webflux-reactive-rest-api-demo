package com.example.webfluxdemo.service;

import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by jjcosare on 7/5/18.
 */
@Service
public class UserService extends AbstractService<UserRepository, User, String> {

    @Autowired
    public UserService(UserRepository userRepository){
        super(userRepository);
    }

}
