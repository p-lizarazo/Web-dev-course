package co.edu.javeriana.pw.back.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.exceptions.NotFoundException;
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

    @GetMapping("/forums/{id}")
    public Forum findById(@PathVariable Long id)
    {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Forum not found"));
    }

    @GetMapping("/forums/{id}/topics")
    public Iterable<Topic> findAllTopics(@PathVariable("id") Long forumId)
    {
        List<Topic> temp = repository.findById(forumId).get().getTemas();
        List<Topic> resp = new ArrayList<>();
        for (Topic topic : temp) {
            if(topic.getAprobado())
                resp.add(topic);
        }
        return resp;
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

    @PutMapping("/forums/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public Forum updateForum(@PathVariable Long id, @RequestBody Forum forumData)
    {
        Forum forum = repository.findById(id).get();
        forum.setDescripcion(forumData.getDescripcion());
        forum.setModerado(forumData.getModerado());
        forum.setTitulo(forumData.getTitulo());

        return repository.save(forum);
    }
}
