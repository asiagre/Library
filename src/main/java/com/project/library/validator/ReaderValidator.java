package com.project.library.validator;

import com.project.library.exception.WrongIdException;
import com.project.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReaderValidator {

    @Autowired
    private ReaderService readerService;

    public void validateReaderId(Long id) {
        if(!readerService.isReaderExist(id)) {
            throw new WrongIdException("Wrong user id");
        }
    }

}
