package com.hadjshell.JobAd.repo;

import com.hadjshell.JobAd.model.JobPost;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

@Repository
public class JobRepo {
    private List<JobPost> jobs = new ArrayList<>(Arrays.asList(

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

    public List<JobPost> getAllJobs() {
        return jobs;
    }

    public void addJob(JobPost job) {
        jobs.add(job);
    }

    public JobPost getJob(int jobId) {
        for (JobPost job : jobs) {
            if (job.getPostId() == jobId)
                return job;
        }

        return null;
    }

    public void updateJob(JobPost newJob) {
        JobPost oldJob = getJob(newJob.getPostId());
        if (oldJob != null) {
            oldJob.setPostDesc(newJob.getPostDesc());
            oldJob.setPostProfile(newJob.getPostProfile());
            oldJob.setReqExperience(newJob.getReqExperience());
            oldJob.setPostTechStack(newJob.getPostTechStack());
        } else {
          addJob(newJob);
        }
    }

    public void deleteJob(int jobId) {
        ListIterator<JobPost> li = jobs.listIterator();
        while (li.hasNext()) {
            JobPost job = li.next();
            if (job.getPostId() == jobId) {
                li.remove();
                break;
            }
        }
    }
}
