package com.project.library.controller;

import com.project.library.domain.CopyDto;
import com.project.library.domain.State;
import com.project.library.domain.TitleDto;
import com.project.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

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

}
