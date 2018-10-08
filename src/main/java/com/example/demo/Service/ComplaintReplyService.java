package com.example.demo.Service;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.ComplaintReply;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
public interface ComplaintReplyService {
    void save(ComplaintReply complaintReply);
    List<ComplaintReply> findAll();
    List<ComplaintReply> findByAgency(String agency);
    List<ComplaintReply> findByAgencyAndUserid(String agency, Long id);
}
