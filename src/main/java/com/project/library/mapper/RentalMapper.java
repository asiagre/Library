package com.project.library.mapper;

import com.project.library.domain.Rental;
import com.project.library.domain.RentalDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RentalMapper {

    public Rental mapToRental(final RentalDto rentalDto) {
        return new Rental(rentalDto.getCopy(), rentalDto.getReader(), rentalDto.getBorrowDate(), rentalDto.getReturnDate());
    }

    public RentalDto mapToRentalDto(final Rental rental) {
        return new RentalDto(rental.getId(), rental.getReader(), rental.getCopy(), rental.getBorrowDate(), rental.getReturnDate());
    }

    public List<RentalDto> mapToRentalDtoList(final List<Rental> rentalList) {
        return rentalList.stream()
                .map(rental -> mapToRentalDto(rental))
                .collect(Collectors.toList());
    }
}
