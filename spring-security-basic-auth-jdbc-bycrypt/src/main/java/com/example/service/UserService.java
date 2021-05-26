package com.example.service;

import com.example.config.UserDetailsSql;
import com.example.model.User;
import com.example.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Iterable<User> getUser() {
        return userRepository.findAll();
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
          User user=userRepository.findByUserName(name);
          if(null==user){
               throw new UsernameNotFoundException("user not found- " + name);
          }
          return new UserDetailsSql(user);
    }
}
