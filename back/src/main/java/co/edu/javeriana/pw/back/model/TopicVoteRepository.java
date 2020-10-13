package co.edu.javeriana.pw.back.model;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface TopicVoteRepository extends CrudRepository<TopicVote,Long> {
    List<TopicVote> findByTema(Topic topic);

    @Query(value = "SELECT COUNT(*) FROM TOPIC_VOTE WHERE TEMA_ID = ?1 AND ME_GUSTA = TRUE",
    nativeQuery = true)
    Long findTopicsLikes(Long topicId);

    @Query(value = "SELECT COUNT(*) FROM TOPIC_VOTE WHERE TEMA_ID = ?1 AND ME_GUSTA = FALSE",
    nativeQuery = true)
    Long findTopicsDislikes(Long topicId);

    Optional<TopicVote> findByOwnerUsernameAndTema(String ownerUsername, Topic topic);
}
