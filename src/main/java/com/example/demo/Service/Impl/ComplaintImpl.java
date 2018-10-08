package com.example.demo.Service.Impl;

//import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.Complaint;
import com.example.demo.Repository.ComplaintRepository;
import com.example.demo.Service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Service("complaintService")
 class ComplaintImpl implements ComplaintService {

    @Autowired
    ComplaintRepository complaintRepository;

    @Override
    public void save(Complaint complaint) {
        complaintRepository.save(complaint);
    }

/*    @Override
    public void merge(Complaint complaint) {
        complaintRepository.merge(complaint);
    }*/

//    @Autowired
//    public void merge(Complaint complaint){
//       complaintRepository.merge(complaint);
//    }


}
