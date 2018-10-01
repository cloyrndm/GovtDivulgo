package com.example.demo.Repository;

import com.example.demo.Entity.GovtUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Repository
public interface GovtUserRepository extends JpaRepository<GovtUser,Long> {
    GovtUser findByUsernameAndPassword(String username, String password);
}

