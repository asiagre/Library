package com.project.library.mapper;

import com.project.library.domain.Copy;
import com.project.library.domain.Reader;
import com.project.library.domain.Rental;
import com.project.library.domain.RentalDto;
import com.project.library.repository.CopyDao;
import com.project.library.repository.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class RentalMapper {
    @Autowired
    private CopyDao copyDao;

    @Autowired
    private ReaderDao userDao;

    public Rental mapToRental(final RentalDto rentalDto) {
        Optional<Copy> copy = copyDao.findById(rentalDto.getCopyId());
        Optional<Reader> reader = userDao.findById(rentalDto.getReaderId());
        return new Rental(rentalDto.getId(), copy.get(), reader.get(), rentalDto.getBorrowDate(), rentalDto.getReturnDate(), rentalDto.isReturned());
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(rental.getId(), rental.getReader().getId(), rental.getCopy().getId(), rental.getBorrowDate(), rental.getReturnDate(), rental.isReturned());
    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(rental -> mapToRentalDto(rental))
                .collect(Collectors.toList());
    }
}
