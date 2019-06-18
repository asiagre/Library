package com.project.library.controller;

import com.project.library.domain.ReaderDto;
import com.project.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private ReaderService readerService;

    @RequestMapping(method = RequestMethod.POST, value = "users", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        readerService.createReader(readerDto);
    }

}
