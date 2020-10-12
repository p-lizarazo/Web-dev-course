package co.edu.javeriana.pw.back.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface TopicRepository extends CrudRepository<Topic, Long> {
    List<Topic> findByAprobado(Boolean bool);
}
