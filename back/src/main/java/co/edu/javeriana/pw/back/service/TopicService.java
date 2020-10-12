package co.edu.javeriana.pw.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.jaas.AuthorityGranter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.exceptions.NotFoundException;
import co.edu.javeriana.pw.back.model.Topic;
import co.edu.javeriana.pw.back.model.TopicRepository;

@RestController
public class TopicService {
    
    @Autowired
    private TopicRepository repository;

    @GetMapping("/topics")
    public Iterable<Topic> findAllTopics()
    {
        return repository.findAll();
    }

    @GetMapping("/topics/{id}")
    public Topic findTopic(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Employee not found"));
    }

    @PostMapping()
    @PreAuthorize("hasRole('ROLE_MOD') or hasRole('ROLE_MOD') or hasUser('ROLE_USER')")
    public Topic createTopic(@RequestBody Topic topic)
    {
        return repository.save(topic);        
    }

}
