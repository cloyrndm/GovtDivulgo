package com.example.demo.Repository;

import com.example.demo.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 08/10/2018.
 */
@Repository
public interface UserRepository  extends JpaRepository<User,Long> {
 User findByUserId(Long id);
}
