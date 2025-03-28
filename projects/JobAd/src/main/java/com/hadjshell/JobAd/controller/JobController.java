package com.hadjshell.JobAd.controller;

import com.hadjshell.JobAd.model.JobPost;
import com.hadjshell.JobAd.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
@CrossOrigin(origins = "http://localhost:3000")
public class JobController {

    private JobService service;

    @Autowired
    public JobController(JobService service) {
        this.service = service;
    }

    public JobController() {
    }

    @GetMapping
    public List<JobPost> getAllJobs() {
        return service.getAllJobs();
    }

    // You can specify which format you want to produce or consume
    @GetMapping(path = "/{postId}", produces = {"application/json"})
    public JobPost getJobById(@PathVariable("postId") int postId) {
        return service.getJobById(postId);
    }

    @GetMapping(params = "keyword")
    public List<JobPost> searchJob(@RequestParam(name = "keyword", defaultValue = "nothing") String keyword) {
        return service.search(keyword);
    }

    @PostMapping(consumes = {"application/json"})
    public JobPost addJob(@RequestBody JobPost job) {
        service.addJob(job);
        return job;
    }

    @PutMapping("/{postId}")
    public JobPost updateJob(@RequestBody JobPost job) {
        service.updateJob(job);
        return service.getJobById(job.getPostId());
    }

    @DeleteMapping
    public void deleteJob(@RequestParam(name = "id", defaultValue = "1") int jobId) {
        service.deleteJob(jobId);
    }
}
