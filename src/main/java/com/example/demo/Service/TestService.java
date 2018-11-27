package com.example.demo.Service;

import com.example.demo.Entity.Test;
import com.example.demo.Repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cloie Andrea on 16/11/2018.
 */

@Service
public class TestService {

    @Autowired
    TestRepository testRepository;

    public void save(Test test){
        testRepository.save(test);
    }
}
