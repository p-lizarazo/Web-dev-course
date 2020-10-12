package co.edu.javeriana.pw.back.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.model.Forum;
import co.edu.javeriana.pw.back.model.ForumRepository;
import co.edu.javeriana.pw.back.model.Topic;

@RestController
public class ForumService {
    
    @Autowired
    private ForumRepository repository;

    @GetMapping("/forums")
    public Iterable<Forum> findAllForums(Authentication authentication)
    {
        return repository.findAll();
    }

    @GetMapping("/forums/{id}/topics")
    public Iterable<Topic> findAllTopics(@PathVariable("id") Long forumId)
    {
        return repository.findById(forumId).get().getTemas();
    }

    @PostMapping("/forums")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Forum createForum(@RequestBody Forum forum)
    {    
        return repository.save(forum);
    }

    @DeleteMapping("/forums/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteForum(@PathVariable("id") Long id)
    {
        Forum f = repository.findById(id).get();
        repository.delete(f);
    }
}
