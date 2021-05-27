package com.example.controller;

import com.example.app.MyApplication;
import com.example.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;

@SpringBootTest(classes = MyApplication.class)
public class UserControllerTest {

    @MockBean
    private UserController userController;


    @Test
    public void testHappyScenario() {
        User user = new User();
        user.setPassword("admin");
        user.setUserName("admin");
        user.setCreationDate(new Date());
         userController.addUser(user);

    }

}
