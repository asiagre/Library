package com.project.library.repository;

import com.project.library.domain.Reader;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface ReaderDao extends CrudRepository<Reader, Long> {

    @Override
    Reader save(Reader reader);

    Optional<Reader> findByLastName(String lastName);

    boolean existsById(long id);

    Optional<Reader> findById(Long id);
}
