package com.example.demo.Service.Impl;


import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.GovtUser;

import com.example.demo.Repository.ComplaintRepository;
import com.example.demo.Repository.GovtUserRepository;
import com.example.demo.Service.GovtUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 01/10/2018.
 */
@Service("govtUserService")
public class GovtUserImpl implements GovtUserService {
    @Autowired
    GovtUserRepository govtUserRepository;
    @Autowired
    ComplaintRepository complaintRepository;



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
    @Override
    public List<Complaint> findAll(){
    return complaintRepository.findAll();
    }

    @Override
    public List<Complaint> findByGovtAgency(String type) {
        return complaintRepository.findByGovtAgency(type);
    }

    @Override
    public List<Complaint> findByGovtAgencyAndStatus(String agency, String stat){
        return complaintRepository.findByGovtAgencyAndStatus(agency,stat);
    }
    @Override
    public Complaint findByComplaintId(Long id){
        return complaintRepository.findByComplaintId(id);
    }

    @Override
    public List<Complaint> findByStatus(String status) {
        return complaintRepository.findByStatus(status);
    }

//    @Override
//    public void update(Complaint complaint){
//        complaintRepository.update(complaint);
//    }

////
//    @Override
//    public List<Complaint> findByComplaintId(){
//        return complaintRepository.findByComplaintId();
//    }
//    @Override
//    public List<Complaint> findAll() {
////        return complaintRepository.findAll();
//        return complaintRepository.findAll();
//    }
}
