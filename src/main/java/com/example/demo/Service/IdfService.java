package com.example.demo.Service;


import com.example.demo.Entity.Idf;
import com.example.demo.Repository.IdfRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Cloie Andrea on 15/11/2018.
 */
@Service
public class IdfService {

    @Autowired
    IdfRepository idfRepository;

    public void save(Idf idf){
        idfRepository.save(idf);
    }

    public List<Idf> findAll(){
        return idfRepository.findAll();
    }

    public Idf findByFreqIdAndNgramId(int id, int nid){
        return idfRepository.findByFreqIdAndNgramId(id,nid);
    }

    public Idf findByFreqId(int id){
        return idfRepository.findByFreqId(id);
    }



}
