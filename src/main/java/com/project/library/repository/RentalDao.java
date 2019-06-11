package com.project.library.repository;

import com.project.library.domain.Copy;
import com.project.library.domain.Reader;
import com.project.library.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
@Transactional
public interface RentalDao extends CrudRepository<Rental, Long> {

    @Override
    Rental save(Rental rental);

    Optional<Rental> findByReader(Reader reader);

    boolean existsById(Long id);

    Optional<Rental> findById(long id);

    Optional<Rental> findByCopy(Copy copy);

}
