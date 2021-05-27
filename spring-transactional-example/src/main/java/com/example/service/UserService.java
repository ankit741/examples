package com.example.service;

import com.example.model.User;
import com.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService  {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUser() {
        return userRepository.findAll();
    }
      /*
      Spring declarative transaction model uses AOP proxy.
      By default propagation level is REQUIRED which means inner transaction will be part of same outer transaction,
      so if inner transaction fails whole transaction will get rollback.
      Now its important to know that Rollback works for only Runtime exceptions by default.
      For checked Exceptions you have to specify that explicitly @Transcations(rollbackFor = Exception.class)
      */
    @Transactional
    public void createUser(User user){
        User userData=userRepository.save(user);
        //operation -1 inserted in DB successfully.
        // if new method () called from here , same connection will be used.
        //Mixing the database I/O with other types of I/O in a transactional context is a code smell. connection can be exhausted very fast
        // It is possible to make a setRollbackOnly() call within a transaction context if necessary.
        if(userData.getId()%2==0){
            // just to show - operation -2 started and failed.
          throw new IllegalArgumentException("Invalid ID");

        }
        // Operation -1 rollback automatically as method is annotated with Transactional.
    }

}
