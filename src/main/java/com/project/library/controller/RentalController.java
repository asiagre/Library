package com.project.library.controller;

import com.project.library.domain.RentalDto;
import com.project.library.service.RentalService;
import com.project.library.validator.BookValidator;
import com.project.library.validator.ReaderValidator;
import com.project.library.validator.RentalValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library/users")
@CrossOrigin(origins = "*")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BookValidator bookValidator;

    @Autowired
    private ReaderValidator readerValidator;

    @Autowired
    private RentalValidator rentalValidator;

    @RequestMapping(method = RequestMethod.POST, value = "{id}/rentals", consumes = APPLICATION_JSON_VALUE)
    public void rentBook(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        readerValidator.validateReaderId(rentalDto.getReaderId());
        bookValidator.validateCopyId(rentalDto.getCopyId());
        rentalValidator.validateIfBorrowed(rentalDto.getCopyId());
        rentalService.rentBook(rentalDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{userId}/rentals/{rentalId}")
    public void returnBook(@PathVariable Long userId, @PathVariable Long rentalId, @RequestParam boolean destroyed, @RequestParam boolean paid) {
        readerValidator.validateReaderId(userId);
        rentalValidator.validateRentalId(rentalId);
        rentalService.returnBook(userId, rentalId, destroyed, paid);
    }

}
