package com.example.demo.Repository;

import com.example.demo.Entity.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Cloie Andrea on 16/11/2018.
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {

}
