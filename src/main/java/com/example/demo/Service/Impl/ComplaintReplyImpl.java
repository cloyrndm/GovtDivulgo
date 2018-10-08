package com.example.demo.Service.Impl;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.ComplaintReply;
import com.example.demo.Repository.ComplaintReplyRepository;
import com.example.demo.Service.ComplaintReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Service("complaintReplyService")
public class ComplaintReplyImpl implements ComplaintReplyService {
    @Autowired
    ComplaintReplyRepository complaintReplyRepository;

    @Override
    public void save(ComplaintReply complaintReply) {
        complaintReplyRepository.save(complaintReply);
    }

    @Override
    public List<ComplaintReply> findAll() {
        return complaintReplyRepository.findAll();
    }

    @Override
    public List<ComplaintReply> findByAgency(String agency) {
        return complaintReplyRepository.findByAgency(agency);
    }

    @Override
    public List<ComplaintReply> findByAgencyAndUserid(String agency, Long id) {
        return complaintReplyRepository.findByAgencyAndUserid(agency,id);
    }
//public void save(ComplaintReply complaintReply){
//    return complaintReplyRepository.save(complaintReply);
//}

}
