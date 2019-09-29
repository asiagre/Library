package com.project.library.validator;

import com.project.library.exception.WrongIdException;
import com.project.library.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BookValidator {

    @Autowired
    private BookService bookService;

    public void validateTitleId(Long id) {
        if(!bookService.isBookExist(id)) {
            throw new WrongIdException("Wrong title id");
        }
    }

    public void validateCopyId(Long id) {
        if(!bookService.isCopyExist(id)) {
            throw new WrongIdException("Wrong copy id");
        }
    }

}
