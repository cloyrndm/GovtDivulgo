package com.example.demo.Repository;

import com.example.demo.Entity.Frequency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Katrina on 10/22/2018.
 */
@Repository
public interface FrequencyRepository extends JpaRepository<Frequency, Integer> {

//    @Query("SELECT Frequency.frequency from Ngram, Frequency where Ngram.words= Frequency.word")
//    List<Frequency> findByNgramId(int ngramId);

    //tfidf
    Frequency findByArtIdAndFreqId(int id, int fid);
    Frequency findByArtIdAndNgramId(int id, int nid);
    List<Frequency> findByNgramId(int id);
    Frequency findByFreqId(int id);
    List<Frequency> findAll();

}
