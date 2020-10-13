package co.edu.javeriana.pw.back.service;

import java.security.Principal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

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
import co.edu.javeriana.pw.back.model.Comment;
import co.edu.javeriana.pw.back.model.CommentRepository;
import co.edu.javeriana.pw.back.model.Topic;
import co.edu.javeriana.pw.back.model.TopicRepository;

@RestController
public class CommentService {
    
    @Autowired
    private CommentRepository repository;

    @Autowired
    private TopicRepository topicRepository;

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    @GetMapping("/comments/all")
    public Iterable<Comment> findAllComments()
    {
        return repository.findAll();
    }

    @GetMapping("/comments")
    public Iterable<Comment> findAllCommentsAprobados()
    {
        return repository.findByAprobado(true);
    }

    @GetMapping("/comments/noaprobados")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Iterable<Comment> findAllCommentsNoAprobados()
    {
        return repository.findByAprobado(false);
    }

    @GetMapping("/comments/{id}/any")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Comment findCommentMod(@PathVariable Long id)
    {
        return repository.findById(id).get();
    }

    @GetMapping("/comments/{id}")
    public Comment findComment(@PathVariable Long id)
    {
        Comment com = repository.findById(id).get();
        if(com.getAprobado())
            return com;
        else
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
    }

    @GetMapping("/topics/{id}/comments")
    public List<Comment> findTopicComments(@PathVariable Long id)
    {
        Topic t = topicRepository.findById(id).get();
        List<Comment> temp = repository.findByTema(t);
        List<Comment> resp = new ArrayList<>();
        for (Comment comment : temp) {
            if(comment.getAprobado())
                resp.add(comment);
        }
        return resp;
    }

    @PostMapping("/comments")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public Comment createComment(@RequestBody Comment comment, Principal principal)
    {
        if(comment.getTema() == null)
            throw new NotFoundException("No pertenece a ningun foro");
        
        comment.setTema(topicRepository.findById(comment.getTema().getId()).get());
        comment.setOwnerUsername(principal.getName());
        comment.setFechaPublicacion(new Date(System.currentTimeMillis()));

        if(comment.getTema().getForo().getModerado())
            comment.setAprobado(false);
        else
            comment.setAprobado(true);
        
        return repository.save(comment);        
    }

    @PutMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public Comment modifyComment(@PathVariable Long id, @RequestBody Comment topic, Authentication auth)
    {
        boolean isAuth = false;
        Comment mComment = repository.findById(id).get();
        for (GrantedAuthority mAuth : auth.getAuthorities()) {
            if(mAuth.getAuthority().equals("ROLE_ADMIN"))
                isAuth = true;
        }
        if(auth.getName().equals(mComment.getOwnerUsername()))
            isAuth = true;

        if(!isAuth)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        
        mComment.setContenido(topic.getContenido());

        return repository.save(mComment);
    }

    @DeleteMapping("/comments/{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public void deleteTopic(@PathVariable("id") Long id, Authentication auth)
    {
        boolean isAuth = false;
        Comment mComment = repository.findById(id).get();
        for (GrantedAuthority mAuth : auth.getAuthorities()) {
            if(mAuth.getAuthority().equals("ROLE_ADMIN"))
                isAuth = true;
        }
        if(auth.getName().equals(mComment.getOwnerUsername()))
            isAuth = true;

        if(!isAuth)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "unauthorized");
        
        Comment c = repository.findById(id).get();
        repository.delete(c);
    }
}
