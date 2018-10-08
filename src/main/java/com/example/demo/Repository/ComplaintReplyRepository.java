package com.example.demo.Repository;

import com.example.demo.Entity.Complaint;
import com.example.demo.Entity.ComplaintReply;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
@Repository
public interface ComplaintReplyRepository extends JpaRepository<ComplaintReply,Long> {
//    void save(ComplaintReply complaintReply);
    List<ComplaintReply> findAll();
    List<ComplaintReply> findByAgency(String agency);
    List<ComplaintReply> findByAgencyAndUserid(String agency, Long id);
}
