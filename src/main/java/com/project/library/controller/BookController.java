package com.project.library.controller;

import com.project.library.domain.CopyDto;
import com.project.library.domain.State;
import com.project.library.domain.TitleDto;
import com.project.library.service.BookService;
import com.project.library.validator.BookValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class BookController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BookValidator bookValidator;

    @RequestMapping(method = RequestMethod.GET, value = "titles")
    public List<TitleDto> getAllBooks() {
        return bookService.getBooks();
    }

    @RequestMapping(method = RequestMethod.POST, value = "titles", consumes = APPLICATION_JSON_VALUE)
    public void addBook(@RequestBody TitleDto titleDto) {
        bookService.addBook(titleDto);
    }

    @RequestMapping(method = RequestMethod.POST, value = "titles/{id}/copies", consumes = APPLICATION_JSON_VALUE)
    public void addCopy(@PathVariable Long id, @RequestBody CopyDto copy) {
        bookValidator.validateTitleId(id);
        bookService.addCopy(id, copy);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "titles/{titleId}/copies/{copyId}", consumes = APPLICATION_JSON_VALUE)
    public void changeState(@PathVariable Long titleId, @PathVariable Long copyId, @RequestParam State state) {
        bookValidator.validateTitleId(titleId);
        bookValidator.validateCopyId(copyId);
        bookService.changeState(copyId, state);
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles/{id}")
    public int howManyCopiesAvailable(@PathVariable Long id) {
        bookValidator.validateTitleId(id);
        return bookService.howManyCopiesAvailable(id);
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles/titles")
    public List<TitleDto> findBooksByTitle(@RequestParam String title) {
        return bookService.findBooksByTitle(title);
    }

    @RequestMapping(method = RequestMethod.GET, value = "titles/authors")
    public List<TitleDto> findBooksByAuthor(@RequestParam String author) {
        return bookService.findBooksByAuthor(author);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "titles/{titleId}")
    public void deleteBook(@PathVariable Long titleId) {
        bookValidator.validateTitleId(titleId);
        if(bookService.howManyCopiesAvailable(titleId) == 0) {
            bookService.deleteBook(titleId);
        }
    }

}
