package com.project.library.controller;

import com.project.library.domain.*;
import com.project.library.service.BookService;
import com.project.library.service.RentalService;
import com.project.library.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class LibraryController {

    @Autowired
    private UserService userService;

    @Autowired
    private BookService bookService;

    @Autowired
    private RentalService rentalService;

    @RequestMapping(method = RequestMethod.POST, value = "users", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        userService.createReader(readerDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "titles", consumes = APPLICATION_JSON_VALUE)
    public void addBook(@RequestBody TitleDto titleDto) {
        bookService.addBook(titleDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "titles/{id}/copies", consumes = APPLICATION_JSON_VALUE)
    public void addCopy(@PathVariable Long id, @RequestBody CopyDto copy) {
        validateTitleId(id);
        bookService.addCopy(id, copy);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "titles/{titleId}/copies/{copyId}", consumes = APPLICATION_JSON_VALUE)
    public void changeState(@PathVariable Long titleId, @PathVariable Long copyId, @RequestParam State state) {
        validateTitleId(titleId);
        validateCopyId(copyId);
        bookService.changeState(copyId, state);
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles/{id}")
    public int howManyCopiesAvailable(@PathVariable Long id) {
        validateTitleId(id);
        return bookService.howManyCopiesAvailable(id);
    }

    @RequestMapping(method = RequestMethod.POST, value = "user/{id}/rental", consumes = APPLICATION_JSON_VALUE)
    public void rentBook(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        validateUserId(rentalDto.getReaderId());
        validateCopyId(rentalDto.getCopyId());
        validateIfBorrowed(rentalDto.getCopyId());
        rentalService.rentABook(rentalDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "user/{userId}/rental/{rentalId}")
    public void returnBook(@PathVariable Long userId, @PathVariable Long rentalId, @RequestParam boolean destroyed, @RequestParam boolean paid) {
        validateUserId(userId);
        validateRentalId(rentalId);
        rentalService.returnBook(rentalId, destroyed, paid);
    }

    private void validateTitleId(Long id) {
        if(!bookService.isBookExist(id)) {
            throw new WrongIdException("Wrong title id");
        }
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
