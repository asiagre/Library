package com.project.library.repository;

import com.project.library.domain.Title;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface TitleDao extends JpaRepository<Title, Long> {

    @Override
    Title save(Title title);

    Optional<Title> findByBookTitle(String bookTitle);

    boolean existsById(Long id);

    @Query(nativeQuery = true)
    List<Title> retrieveBookByTitle(@Param("TITLE") String title);

    @Query(nativeQuery = true)
    List<Title> retrieveBookByAuthor(@Param("AUTHOR") String author);

}
