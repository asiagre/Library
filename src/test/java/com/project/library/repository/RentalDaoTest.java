package com.project.library.repository;

import com.project.library.domain.*;
import com.project.library.mapper.RentalMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RentalDaoTest {

    @Autowired
    private RentalDao rentalDao;

    @Autowired
    private RentalMapper rentalMapper;

    @Test
    public void shouldCreateRental() {
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);

        //When
        rentalDao.save(rental);
        Optional<Rental> rentalFromDb = rentalDao.findByReader(reader);

        //Then
        Assert.assertNotEquals("0", rentalFromDb.get().getId());

        //CleanUp
        rentalDao.delete(rentalFromDb.get().getId());
    }

    @Test
    public void shouldReturnBook() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);
        rentalDao.save(rental);
        RentalDto rentalDto = rentalMapper.mapToRentalDto(rental);

        //When
        rentalDto.setReturnDate(LocalDate.now());
        rentalDao.save(rentalMapper.mapToRental(rentalDto));
        Optional<Rental> rentalFromDb = rentalDao.findByReader(reader);

        //Then
        Assert.assertNotEquals(null, rentalFromDb.get().getReturnDate());

        //CleanUp
        rentalDao.delete(rentalFromDb.get().getId());
    }

    @Test
    public void shouldFindRentalIfExists() {
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);
        rentalDao.save(rental);

        //When
        long id = rentalDao.findAll().get(0).getId();
        boolean isRented = rentalDao.existsById(id);
        boolean isNotRented = rentalDao.existsById(200L);

        //Then
        Assert.assertTrue(isRented);
        Assert.assertFalse(isNotRented);

        //CleanUp
        rentalDao.delete(id);
    }

}