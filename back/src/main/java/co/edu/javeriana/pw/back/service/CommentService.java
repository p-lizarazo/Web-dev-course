package co.edu.javeriana.pw.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.model.CommentRepository;

@RestController
public class CommentService {
    
    @Autowired
    private CommentRepository repository;
    

}
