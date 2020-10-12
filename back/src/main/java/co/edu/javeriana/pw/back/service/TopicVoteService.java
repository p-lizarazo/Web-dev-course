package co.edu.javeriana.pw.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.model.TopicVoteRepository;

@RestController
public class TopicVoteService {
    @Autowired
    private TopicVoteRepository repository;

    
}
