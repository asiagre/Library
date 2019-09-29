package com.project.library.repository;

import com.project.library.domain.Copy;
import com.project.library.domain.State;
import com.project.library.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
@Transactional
public interface CopyDao extends JpaRepository<Copy, Long> {

    Copy findOne(Long id);

    Optional<Copy> findById(Long id);

    boolean existsById(Long id);

    Optional<Copy> findByTitleAndState(Title title, State state);


}
