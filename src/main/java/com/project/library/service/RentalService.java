package com.project.library.service;

import com.project.library.domain.Copy;
import com.project.library.domain.Rental;
import com.project.library.domain.RentalDto;
import com.project.library.domain.State;
import com.project.library.mapper.RentalMapper;
import com.project.library.repository.RentalDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class RentalService {

    @Autowired
    private RentalDao rentalDao;

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalMapper rentalMapper;

    public RentalDto rentABook(RentalDto rentalDto) {
        rentalDto.setBorrowDate(LocalDate.now());
        Rental rental = rentalDao.save(rentalMapper.mapToRental(rentalDto));
        bookService.changeState(rentalDto.getCopyId(), State.BORROWED);
        return rentalMapper.mapToRentalDto(rental);
    }

    public Rental returnBook(Long rentalId) {
        Optional<Rental> rental = rentalDao.findById(rentalId);
        RentalDto rentalDto = rentalMapper.mapToRentalDto(rental.get());
        rentalDto.setReturnDate(LocalDate.now());
        bookService.changeState(rentalDto.getCopyId(), State.PREOWNED);
        return rentalDao.save(rentalMapper.mapToRental(rentalDto));
    }

    public boolean isRentalExist(Long id) {
        return rentalDao.existsById(id);
    }
}
