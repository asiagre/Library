package com.project.library.repository;

import com.project.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface TitleDao extends CrudRepository<Title, Long> {

    @Override
    Title save(Title title);

    Optional<Title> findByBookTitle(String bookTitle);

    boolean existsById(Long id);

}
