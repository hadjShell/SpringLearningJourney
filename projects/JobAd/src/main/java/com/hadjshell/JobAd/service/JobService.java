package com.hadjshell.JobAd.service;

import com.hadjshell.JobAd.model.JobPost;
import com.hadjshell.JobAd.repo.JobRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class JobService {
    private JobRepo repo;

    @Autowired
    public JobService(JobRepo repo) {
        this.repo = repo;
        List<JobPost> jobs = new ArrayList<>(Arrays.asList(

                new JobPost(1, "Java Developer", "Must have good experience in core Java and advanced Java", 2,
                        List.of("Core Java", "J2EE", "Spring Boot", "Hibernate")),


                new JobPost(2, "Frontend Developer", "Experience in building responsive web applications using React", 3,
                        List.of("HTML", "CSS", "JavaScript", "React")),


                new JobPost(3, "Data Scientist", "Strong background in machine learning and data analysis", 4,
                        List.of("Python", "Machine Learning", "Data Analysis")),


                new JobPost(4, "Network Engineer", "Design and implement computer networks for efficient data communication", 5,
                        List.of("Networking", "Cisco", "Routing", "Switching")),


                new JobPost(5, "Mobile App Developer", "Experience in mobile app development for iOS and Android", 3,
                        List.of("iOS Development", "Android Development", "Mobile App"))
        ));
        repo.saveAll(jobs);
    }

    public JobService() {
    }

    public void addJob(JobPost job) {
        repo.save(job);
    }

    public List<JobPost> getAllJobs() {
        return repo.findAll();
    }

    public JobPost getJobById(int jobId) {
        return repo.findById(jobId).orElse(null);
    }

    public List<JobPost> search(String keyword) {
        return repo.findByPostProfileContaining(keyword);
    }

    public void updateJob(JobPost job) {
        repo.save(job);
    }

    public void deleteJob(int jobId) {
        repo.deleteById(jobId);
    }
}
