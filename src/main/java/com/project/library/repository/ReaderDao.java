package com.project.library.repository;

import com.project.library.domain.Reader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface ReaderDao extends JpaRepository<Reader, Long> {

    @Query(nativeQuery = true)
    List<Reader> retrieveReadersWhereLastnameFragmentIs(@Param("LASTNAME") String lastName);

    boolean existsById(long id);

    Optional<Reader> findById(Long id);

    void removeById(Long id);
}
