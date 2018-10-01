package com.example.demo.Service;

import com.example.demo.Entity.GovtUser;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
public interface GovtUserService {
    GovtUser findByUsernameAndPassword(String username, String password);
}
