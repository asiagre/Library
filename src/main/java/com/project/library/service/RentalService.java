package com.project.library.service;

import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.RentalMapper;
import com.project.library.repository.CopyDao;
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
    private CopyDao copyDao;

    @Autowired
    private RentalMapper rentalMapper;

    @Autowired
    private CopyMapper copyMapper;

    public RentalDto rentABook(RentalDto rentalDto) {
        rentalDto.setBorrowDate(LocalDate.now());
        Rental rental = rentalDao.save(rentalMapper.mapToRental(rentalDto));
        changeState(rentalDto.getCopyId(), State.BORROWED);
        return rentalMapper.mapToRentalDto(rental);
    }

    public Rental returnBook(Long rentalId, boolean destroyed, boolean paid) {
        Optional<Rental> rental = rentalDao.findById(rentalId);
        RentalDto rentalDto = rentalMapper.mapToRentalDto(rental.get());
        if(destroyed) {
            if(paid) {
                changeState(rentalDto.getCopyId(), State.DESTROYED);
            } else {
                throw new RuntimeException("The reader has destroyed or lost a book and did not pay for it");
            }
        } else {
            changeState(rentalDto.getCopyId(), State.PREOWNED);
        }
        rentalDto.setReturnDate(LocalDate.now());
        return rentalDao.save(rentalMapper.mapToRental(rentalDto));
    }

    public boolean isRentalExist(Long id) {
        return rentalDao.existsById(id);
    }

    private CopyDto changeState(Long copyId, State state) {
        Copy copy = copyDao.findOne(copyId);
        copy.setState(state);
        return copyMapper.mapToCopyDto(copyDao.save(copy));
    }
}
