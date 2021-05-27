package com.hibernate.basic;

import com.hibernate.basic.entity.User;
import com.hibernate.basic.session.HibernateSessionUtil;
import org.hibernate.Session;

import java.util.Date;

public class HelloWorld {

    public static void main(String[] args) {
        Session session = HibernateSessionUtil.getSessionFactory().openSession();
        session.beginTransaction();

        //Add new Employee object
        User user = new User();
        user.setUserName("hibernate");
        user.setPassword("hibernate");
        user.setCreationDate(new Date());

        //Save the user
        session.save(user);

        //Commit the transaction
        session.getTransaction().commit();
        session.close();

    }
}
