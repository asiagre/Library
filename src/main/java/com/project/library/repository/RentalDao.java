package com.project.library.repository;

import com.project.library.domain.Rental;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface RentalDao extends CrudRepository<Rental, Long> {

}
