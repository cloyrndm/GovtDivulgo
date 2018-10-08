package com.example.demo.Service;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.GovtUser;
import com.example.demo.Entity.User;

import java.util.List;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
public interface GovtUserService {
    GovtUser findByUsernameAndPassword(String username, String password);
    List<Complaint> findAll();
    List<Complaint> findByGovtAgency(String type);
    List<Complaint> findByGovtAgencyAndStatus(String agency, String stat);
    Complaint findByComplaintId(Long id);
    List<Complaint> findByStatus(String status);
    User findByUserId(Long id);
//    void update(Complaint complaint);
//    List<Complaint> findByComplaintId();
}


