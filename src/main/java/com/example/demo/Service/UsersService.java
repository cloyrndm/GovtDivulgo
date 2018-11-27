package com.example.demo.Service;

import com.example.demo.Entity.Users;
import com.example.demo.Repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cloie Andrea on 25/11/2018.
 */
@Service
public class UsersService {

    @Autowired
    private UsersRepository usersRepository;


    public Users findUserByUsername(String username, String password) {

        return usersRepository.findByUsernameAndPassword(username,password);
    }

    public void saveUser(Users user) {
        usersRepository.save(user);
    }

    public Users findUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }
}
