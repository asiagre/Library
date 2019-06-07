package com.project.library.controller;

import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.TitleMapper;
import com.project.library.service.BookService;
import com.project.library.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        validateId(id);
        bookService.addCopy(id, copy);
    }

    private void validateId(Long id) {
        if(!bookService.isBookExist(id)) {
            throw new RuntimeException();
        }
    }
}
