package com.project.library.repository;

import com.project.library.domain.Copy;
import com.project.library.domain.State;
import com.project.library.domain.Title;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Id;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface CopyDao extends CrudRepository<Copy, Long> {

    @Override
    Copy save(Copy copy);

    Optional<Copy> findById(Long id);

    Optional<Copy> findByTitle(Title title);

    Optional<Copy> findByTitleAndState(Title title, State state);


}
