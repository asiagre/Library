package com.project.library.controller;

import com.project.library.domain.Copy;
import com.project.library.domain.RentalDto;
import com.project.library.domain.State;
import com.project.library.service.BookService;
import com.project.library.service.RentalService;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library/user")
@CrossOrigin(origins = "*")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    @Autowired
    private BookService bookService;

    @Autowired
    private UserService userService;

    @RequestMapping(method = RequestMethod.POST, value = "{id}/rental", consumes = APPLICATION_JSON_VALUE)
    public void rentBook(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        validateUserId(rentalDto.getReaderId());
        validateCopyId(rentalDto.getCopyId());
        validateIfBorrowed(rentalDto.getCopyId());
        rentalService.rentABook(rentalDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "{userId}/rental/{rentalId}")
    public void returnBook(@PathVariable Long userId, @PathVariable Long rentalId, @RequestParam boolean destroyed, @RequestParam boolean paid) {
        validateUserId(userId);
        validateRentalId(rentalId);
        rentalService.returnBook(rentalId, destroyed, paid);
    }

    private void validateCopyId(Long id) {
        if(!bookService.isCopyExist(id)) {
            throw new WrongIdException("Wrong copy id");
        }
    }

    private void validateUserId(Long id) {
        if(!userService.isUserExist(id)) {
            throw new WrongIdException("Wrong user id");
        }
    }

    private void validateIfBorrowed(Long id) {
        Copy copy = bookService.getCopy(id).get();
        if(copy.getState() != State.PREOWNED) {
            throw new BookNotAvailableException("This copy is not available");
        }
    }

    private void validateRentalId(Long id) {
        if(!rentalService.isRentalExist(id)) {
            throw new WrongIdException("Wrong rental id");
        }
    }

}
