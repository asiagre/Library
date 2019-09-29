package com.project.library.validator;

import com.project.library.domain.Copy;
import com.project.library.domain.State;
import com.project.library.exception.BookNotAvailableException;
import com.project.library.exception.WrongIdException;
import com.project.library.service.BookService;
import com.project.library.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalValidator {

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalService rentalService;

    public void validateIfBorrowed(Long id) {
        Copy copy = bookService.getCopy(id).get();
        if(copy.getState() != State.PREOWNED) {
            throw new BookNotAvailableException("This copy is not available");
        }
    }

    public void validateRentalId(Long id) {
        if(!rentalService.isBorrowed(id)) {
            throw new WrongIdException("Wrong rental id");
        }
    }

}
