package co.edu.javeriana.pw.back.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface CommentVoteRepository extends CrudRepository<CommentVote,Long> {
    List<CommentVote> findByComentario(Comment comment);

    @Query(value = "SELECT COUNT(*) FROM COMMENT_VOTE WHERE COMENTARIO_ID = ?1 AND ME_GUSTA = TRUE",
    nativeQuery = true)
    Long findCommentLikes(Long commentId);

    @Query(value = "SELECT COUNT(*) FROM COMMENT_VOTE WHERE COMENTARIO_ID = ?1 AND ME_GUSTA = FALSE",
    nativeQuery = true)
    Long findCommentDislikes(Long commentId);

    Optional<CommentVote> findByOwnerUsernameAndComentario(String ownerUsername, Comment comment);
}
