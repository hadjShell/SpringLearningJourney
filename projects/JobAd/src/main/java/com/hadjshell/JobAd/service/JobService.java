package com.hadjshell.JobAd.service;

import com.hadjshell.JobAd.model.JobPost;
import com.hadjshell.JobAd.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JobService {
    private JobRepo repo;

    @Autowired
    public JobService(JobRepo repo) {
        this.repo = repo;
    }

    public JobService() {
    }

    public void addJob(JobPost job) {
        repo.addJob(job);
    }

    public List<JobPost> getAllJobs() {
        return repo.getAllJobs();
    }

    public JobPost getJobById(int jobId) {
        return repo.getJob(jobId);
    }

    public void updateJob(JobPost job) {
        repo.updateJob(job);
    }

    public void deleteJob(int jobId) {
        repo.deleteJob(jobId);
    }
}
