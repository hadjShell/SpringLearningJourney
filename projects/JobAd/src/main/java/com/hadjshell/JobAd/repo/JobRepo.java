package com.hadjshell.JobAd.repo;

import com.hadjshell.JobAd.model.JobPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface JobRepo extends JpaRepository<JobPost, Integer> {

    @Query("SELECT j FROM JobPost j WHERE j.reqExperience >= :year")
    List<JobPost> findByReqExperienceGreaterOrEqualThan(@Param("year") int year);

    List<JobPost> findByPostProfileContaining(String keyword);
}
