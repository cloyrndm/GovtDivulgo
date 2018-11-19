package com.example.demo.Service;

import com.example.demo.Entity.Complaint;

/**
 * Created by Cloie Andrea on 02/10/2018.
 */
public interface ComplaintService {
//    void merge(Complaint complaint);
void save(Complaint complaint);
    Complaint findByComplaintId(Long id);
}
