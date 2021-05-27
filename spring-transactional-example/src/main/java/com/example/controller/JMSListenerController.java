package com.example.controller;

import com.example.model.User;
import com.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class JMSListenerController {
    @Autowired
    private UserService userService;

    @JmsListener(destination = "userQueue")
    public void receiveUserMessage(User user) {
        user.setUserName(user.getUserName()+"JMS_QUEUE"); // just to show user created form message broker.
        userService.createUser(user);
    }

}
