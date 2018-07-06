package com.example.webfluxdemo.controller;

import com.example.webfluxdemo.model.User;
import com.example.webfluxdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by jjcosare on 7/5/18.
 */
@RestController
@RequestMapping("/user")
public class UserController extends AbstractController<UserService, User, String> {

    @Autowired
    public UserController(UserService UserService){
        super(UserService);
    }

}
