package ksi.szybkiprzepis.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ksi.szybkiprzepis.models.Tag;
import ksi.szybkiprzepis.repositories.TagRepository;

@Service
public class TagService {
    @Autowired
    private TagRepository repository;

    public List<Tag> findAll() {
        return repository.findAll();
    }

    public Optional<Tag> findById(Long id) {
        return repository.findById(id);
    }

    public Tag save(Tag tag) {
        return repository.save(tag);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}