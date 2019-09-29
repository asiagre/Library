package com.project.library.service;

import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.RentalMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.ReaderDao;
import com.project.library.repository.RentalDao;
import org.apache.tomcat.jni.Local;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RentalServiceTest {

    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalDao rentalDao;

    @Mock
    private ReaderDao readerDao;

    @Mock
    private CopyDao copyDao;

    @Mock
    private RentalMapper rentalMapper;

    @Mock
    private CopyMapper copyMapper;

    @Test
    public void shouldRentBook() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        CopyDto copyDto = new CopyDto(1L, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);
        RentalDto rentalDto = new RentalDto(1L, 1L, 1L, LocalDate.of(2019, 02, 28), LocalDate.of(2019, 03, 20), false);
        when(rentalDao.save(rental)).thenReturn(rental);
        when(rentalMapper.mapToRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);
        when(copyDao.findOne(1L)).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);
        when(copyDao.save(copy)).thenReturn(copy);

        //When
        RentalDto rentalFromService = rentalService.rentBook(rentalDto);

        //Then
        Assert.assertEquals(LocalDate.now(), rentalFromService.getBorrowDate());
    }

    @Test
    public void returnBook() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        CopyDto copyDto = new CopyDto(1L, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);
        RentalDto rentalDto = new RentalDto(1L, 1L, 1L, LocalDate.of(2019, 02, 28), LocalDate.of(2019, 03, 20), false);
        rentalDto.setReturnDate(LocalDate.now());
        when(rentalDao.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);
        when(readerDao.findById(1L)).thenReturn(Optional.of(reader));
        when(readerDao.save(reader)).thenReturn(reader);
        when(rentalDao.save(rental)).thenReturn(rental);
        when(rentalMapper.mapToRental(rentalDto)).thenReturn(rental);
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);
        when(copyDao.findOne(1L)).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);
        when(copyDao.save(copy)).thenReturn(copy);

        //When
        RentalDto rentalFromService = rentalService.rentBook(rentalDto);

        //Then
        Assert.assertEquals(LocalDate.now(), rentalFromService.getReturnDate());
    }

    @Test
    public void isBorrowed() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        CopyDto copyDto = new CopyDto(1L, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now(), false);
        RentalDto rentalDto = new RentalDto(1L, 1L, 1L, LocalDate.of(2019, 02, 28), LocalDate.of(2019, 03, 20), false);
        when(rentalDao.existsById(1L)).thenReturn(true);
        when(rentalDao.existsById(2L)).thenReturn(false);
        when(rentalDao.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalMapper.mapToRentalDto(rental)).thenReturn(rentalDto);

        //When
        boolean borrowed1 = rentalService.isBorrowed(1L);
        boolean borrowed2 = rentalService.isBorrowed(2L);

        //Then
        Assert.assertTrue(borrowed1);
        Assert.assertFalse(borrowed2);
    }
}