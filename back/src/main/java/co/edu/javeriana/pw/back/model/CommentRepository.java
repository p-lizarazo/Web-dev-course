package co.edu.javeriana.pw.back.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CommentRepository extends CrudRepository<Comment,Long> {
    List<Comment> findByAprobado(Boolean bool);
}
