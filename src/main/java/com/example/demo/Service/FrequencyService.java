package com.example.demo.Service;

import com.example.demo.Entity.Frequency;
import com.example.demo.Repository.FrequencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * Created by Katrina on 10/22/2018.
 */
@Service
public class FrequencyService {

    @Autowired
    private FrequencyRepository frequencyRepository;

    public void save(Frequency frequency){

        frequencyRepository.save(frequency);
    }
//
//    public List <Frequency> findByNgramId(int ngramId){
//
//        return frequencyRepository.findByNgramId(ngramId);
//    }

    public List<Frequency> findByNgramId(int id){
        return frequencyRepository.findByNgramId(id);
    }


    public List<Frequency> getAll(){

        return frequencyRepository.findAll();
    }

    public List<Frequency> findAll(){
        return frequencyRepository.findAll();
    }

    public Frequency findByArtIdAndFreqId(int ai, int fi){
        return frequencyRepository.findByArtIdAndFreqId(ai,fi);
    }


//    public List <Frequency> findByNgramIdandFreqId(int ngramId, int freqId) {
//
//        return (List<Frequency>) frequencyRepository.findByNgramIdandFreqId(ngramId,freqId);
//    }
}
