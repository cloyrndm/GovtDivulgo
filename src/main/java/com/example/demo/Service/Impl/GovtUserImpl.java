package com.example.demo.Service.Impl;

import com.example.demo.Entity.GovtUser;
import com.example.demo.Repository.GovtUserRepository;
import com.example.demo.Service.GovtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Service("govtUserService")
public class GovtUserImpl implements GovtUserService {
    @Autowired
    GovtUserRepository govtUserRepository;

    @Override
    public GovtUser findByUsernameAndPassword(String username, String password) {
       GovtUser user = new GovtUser();
        if(govtUserRepository.findByUsernameAndPassword(username,password) == null) {
            System.out.println("INCORRECT USERNAME AND PASSWORD");
            return null;
        }
        else {
//            System.out.println(user.getFirstName());

            return govtUserRepository.findByUsernameAndPassword(username,password);
        }
    }
}
