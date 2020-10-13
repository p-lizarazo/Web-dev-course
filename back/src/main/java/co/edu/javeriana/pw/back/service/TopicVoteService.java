package co.edu.javeriana.pw.back.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import co.edu.javeriana.pw.back.model.Topic;
import co.edu.javeriana.pw.back.model.TopicRepository;
import co.edu.javeriana.pw.back.model.TopicVote;
import co.edu.javeriana.pw.back.model.TopicVoteRepository;

@RestController
public class TopicVoteService {
    @Autowired
    private TopicVoteRepository repository;

    @Autowired
    private TopicRepository topicRepository;

    @GetMapping("topics/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Iterable<TopicVote> findAllCommentsVotes()
    {
        return repository.findAll();
    }

    @GetMapping("topics/{id}/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public List<TopicVote> findCommentsVoteById(@PathVariable Long id)
    {
        Topic topic = topicRepository.findById(id).get();
        return repository.findByTema(topic);
    }

    @GetMapping("topics/{id}/likes")
    public Long findTopicsLikes(@PathVariable Long id)
    {
        return repository.findTopicsLikes(id);
    }
    
    @GetMapping("topics/{id}/dislikes")
    public Long findTopicsDislikes(@PathVariable Long id)
    {
        return repository.findTopicsDislikes(id);
    }
    
    @PostMapping("topics/{id}/likes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public TopicVote createLike(@PathVariable Long id, Authentication auth)
    {
        Topic mTopic = topicRepository.findById(id).get();
        try {
            TopicVote tv = repository.findByOwnerUsernameAndTema(auth.getName(), mTopic).get();
            if(!tv.getMeGusta())
                tv.setMeGusta(true);
            return repository.save(tv);
        } catch(NoSuchElementException ex) {
            TopicVote newVote = new TopicVote();
            newVote.setMeGusta(true);
            newVote.setOwnerUsername(auth.getName());
            newVote.setTema(mTopic);
            return repository.save(newVote);
        }
    }

    @PostMapping("topics/{id}/dislikes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public TopicVote createDislike(@PathVariable Long id, Authentication auth)
    {
        Topic mTopic = topicRepository.findById(id).get();
        try {
            TopicVote tv = repository.findByOwnerUsernameAndTema(auth.getName(), mTopic).get();
            if(tv.getMeGusta())
                tv.setMeGusta(false);
            return repository.save(tv);
        } catch(NoSuchElementException ex) {
            TopicVote newVote = new TopicVote();
            newVote.setMeGusta(true);
            newVote.setOwnerUsername(auth.getName());
            newVote.setTema(mTopic);
            return repository.save(newVote);
        }
    }

    @DeleteMapping("topics/{id}/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public void deleteLikeFromTopic(@PathVariable Long id, Authentication auth)
    {
        Topic mTopic = topicRepository.findById(id).get();
        TopicVote mVote = repository.findByOwnerUsernameAndTema(auth.getName(), mTopic).get();
        repository.delete(mVote);
    }
    
}
