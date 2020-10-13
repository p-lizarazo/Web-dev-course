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

import co.edu.javeriana.pw.back.model.Comment;
import co.edu.javeriana.pw.back.model.CommentRepository;
import co.edu.javeriana.pw.back.model.CommentVote;
import co.edu.javeriana.pw.back.model.CommentVoteRepository;

@RestController
public class CommentVoteService {
    @Autowired
    private CommentVoteRepository repository;

    @Autowired
    private CommentRepository commentRepository;
    
    @GetMapping("comments/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public Iterable<CommentVote> findAllCommentsVotes()
    {
        return repository.findAll();
    }

    @GetMapping("comments/{id}/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD')")
    public List<CommentVote> findCommentsVoteById(@PathVariable Long id)
    {
        Comment comment = commentRepository.findById(id).get();
        return repository.findByComentario(comment);
    }
    
    @GetMapping("comments/{id}/likes")
    public Long findCommentsLikes(@PathVariable Long id)
    {
        return repository.findCommentLikes(id);
    }

    @GetMapping("comments/{id}/ranking")
    public Long findCommentsRanking(@PathVariable Long id)
    {
        return (repository.findCommentLikes(id) - repository.findCommentDislikes(id));
    }
    
    @GetMapping("comments/{id}/dislikes")
    public Long findCommentsDislikes(@PathVariable Long id)
    {
        return repository.findCommentDislikes(id);
    }

    @GetMapping("comments/{id}/myvote")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public boolean findMyVoteInTopic(@PathVariable Long id, Authentication auth)
    {
        Comment comment = commentRepository.findById(id).get();
        return repository.findByOwnerUsernameAndComentario(auth.getName(), comment).get().getMeGusta();
    }

    @PostMapping("comments/{id}/likes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public CommentVote createLike(@PathVariable Long id, Authentication auth)
    {
        Comment mComment = commentRepository.findById(id).get();
        try {
            CommentVote cv = repository.findByOwnerUsernameAndComentario(auth.getName(), mComment).get();
            if(!cv.getMeGusta()){
                cv.setMeGusta(true);
                return repository.save(cv);
            } else {
                repository.delete(cv);
                return new CommentVote();
            }
        } catch(NoSuchElementException ex) {
            CommentVote newVote = new CommentVote();
            newVote.setMeGusta(true);
            newVote.setOwnerUsername(auth.getName());
            newVote.setComentario(mComment);
            return repository.save(newVote);
        }
    }

    @PostMapping("comments/{id}/dislikes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public CommentVote createDislike(@PathVariable Long id, Authentication auth)
    {
        Comment mComment = commentRepository.findById(id).get();
        try {
            CommentVote cv = repository.findByOwnerUsernameAndComentario(auth.getName(), mComment).get();
            if(cv.getMeGusta()){
                cv.setMeGusta(false);
                return repository.save(cv);
            } else {
                repository.delete(cv);
                return new CommentVote();
            }
        } catch(NoSuchElementException ex) {
            CommentVote newVote = new CommentVote();
            newVote.setMeGusta(false);
            newVote.setOwnerUsername(auth.getName());
            newVote.setComentario(mComment);
            return repository.save(newVote);
        }
    }

    @DeleteMapping("comments/{id}/votes")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_MOD','ROLE_USER')")
    public void deleteLikeFromComments(@PathVariable Long id, Authentication auth)
    {
        Comment mComment = commentRepository.findById(id).get();
        CommentVote mVote = repository.findByOwnerUsernameAndComentario(auth.getName(), mComment).get();
        repository.delete(mVote);
    }

}
