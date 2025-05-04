package ksi.szybkiprzepis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ksi.szybkiprzepis.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
