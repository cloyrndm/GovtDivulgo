package com.example.demo.Repository;

import com.example.demo.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 25/11/2018.
 */
@Repository
public interface UsersRepository extends JpaRepository<Users, Integer> {
    Users findByUsernameAndPassword(String username,String password);
    Users findByEmail(String email);
}
