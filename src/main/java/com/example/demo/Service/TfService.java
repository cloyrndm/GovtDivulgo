package com.example.demo.Service;


import com.example.demo.Entity.Tf;
import com.example.demo.Repository.IdfRepository;
import com.example.demo.Repository.TfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Cloie Andrea on 14/11/2018.
 */
@Service
public class TfService {
    @Autowired
    TfRepository tfRepository;

    @Autowired
    IdfRepository idfRepository;

    public void save(Tf tf){
        tfRepository.save(tf);
    }

    public Tf findByNgramIdAndFreqId(int id,int fid){
        return  tfRepository.findByNgramId(id);
    }

//    public Tf findByFreqId(int id){
//        return tfRepository.findByFreqId(id);
//    }

    public Tf findByFreqId(int id){
        return tfRepository.findByFreqId(id);
    }

    public List<Tf> findAll(){
        return tfRepository.findAll();
    }

}
