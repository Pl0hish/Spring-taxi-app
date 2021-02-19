package com.mnyshenko.taxiSpringApp.service;

import com.mnyshenko.taxiSpringApp.dao.UserRepository;
import com.mnyshenko.taxiSpringApp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public void save(User user) {
        userRepository.save(user);
    }
}
