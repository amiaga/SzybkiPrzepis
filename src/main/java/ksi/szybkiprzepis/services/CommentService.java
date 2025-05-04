package ksi.szybkiprzepis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksi.szybkiprzepis.models.Comment;
import ksi.szybkiprzepis.repositories.CommentRepository;

@Service
public class CommentService {
    @Autowired
    private CommentRepository repository;

    public List<Comment> findAll() {
        return repository.findAll();
    }

    public Optional<Comment> findById(Long id) {
        return repository.findById(id);
    }

    public Comment save(Comment comment) {
        return repository.save(comment);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}