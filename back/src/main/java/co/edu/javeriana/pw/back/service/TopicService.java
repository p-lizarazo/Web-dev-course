package co.edu.javeriana.pw.back.service;

import java.security.Principal;
import java.sql.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import co.edu.javeriana.pw.back.exceptions.NotFoundException;
import co.edu.javeriana.pw.back.model.ForumRepository;
import co.edu.javeriana.pw.back.model.Topic;
import co.edu.javeriana.pw.back.model.TopicRepository;

@RestController
public class TopicService {
    
    @Autowired
    private TopicRepository repository;

    @Autowired
    private ForumRepository forumRepo;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    @GetMapping("/topics/all")
    public Iterable<Topic> findAllTopics()
    {
        return repository.findAll();
    }

    @GetMapping("/topics")
    public Iterable<Topic> findAllTopicsAprobadas()
    {
        return repository.findByAprobado(true);
    }

    @GetMapping("/topics/noaprobados")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Iterable<Topic> findAllTopicsNoAprobadas()
    {
        return repository.findByAprobado(false);
    }

    @PostMapping("/topics/{id}/aprobar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Topic aprobarTopic(@PathVariable Long id)
    {
        Topic t = repository.findById(id).get();
        t.setAprobado(true);
        return repository.save(t);
    }

    @PostMapping("/topics/{id}/rechazar")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public void rechazarTopic(@PathVariable Long id)
    {
        Topic t = repository.findById(id).get();
        repository.delete(t);
    }

    @GetMapping("/topics/{id}/any")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Topic findTopicMod(@PathVariable Long id)
    {
        return repository.findById(id).get();
    }

    @GetMapping("/topics/{id}")
    public Topic findTopic(@PathVariable Long id)
    {
        Topic top = repository.findById(id).get();
        if(top.getAprobado())
            return top;
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
    }

    @PostMapping("/topics")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public Topic createTopic(@RequestBody Topic topic,Principal principal)
    {
        if(topic.getForo() == null)
            throw new NotFoundException("No pertenece a ningun foro");
        
        topic.setForo(forumRepo.findById(topic.getForo().getId()).get());
        topic.setOwnerUsername(principal.getName());
        topic.setFechaPublicacion(new Date(System.currentTimeMillis()));

        if(topic.getForo().getModerado())
            topic.setAprobado(false);
        else
            topic.setAprobado(true);
        
        return repository.save(topic);        
    }

    @PutMapping("/topics/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public Topic modifyTopic(@PathVariable Long id, @RequestBody Topic topic, Authentication auth)
    {
        boolean isAuth = false;
        Topic mTopic = repository.findById(id).get();
        for (GrantedAuthority mAuth : auth.getAuthorities()) {
            if(mAuth.getAuthority().equals("ROLE_ADMIN"))
                isAuth = true;
        }
        if(auth.getName().equals(mTopic.getOwnerUsername()))
            isAuth = true;

        if(!isAuth)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        
        mTopic.setContenido(topic.getContenido());
        mTopic.setTitulo(topic.getTitulo());
        return repository.save(mTopic);
    }

    @DeleteMapping("/topics/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public void deleteTopic(@PathVariable("id") Long id, Authentication auth)
    {
        boolean isAuth = false;
        Topic mTopic = repository.findById(id).get();
        for (GrantedAuthority mAuth : auth.getAuthorities()) {
            if(mAuth.getAuthority().equals("ROLE_ADMIN"))
                isAuth = true;
        }
        if(auth.getName().equals(mTopic.getOwnerUsername()))
            isAuth = true;

        if(!isAuth)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        
        Topic t = repository.findById(id).get();
        repository.delete(t);
    }

}
