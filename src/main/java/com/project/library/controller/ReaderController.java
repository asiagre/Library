package com.project.library.controller;

import com.project.library.domain.ReaderDto;
import com.project.library.exception.WrongIdException;
import com.project.library.service.ReaderService;
import com.project.library.validator.ReaderValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1/library")
@CrossOrigin(origins = "*")
public class ReaderController {

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ReaderValidator readerValidator;

    @RequestMapping(method = RequestMethod.GET, value = "readers/{readerId}")
    public ReaderDto getReader(@PathVariable Long readerId) {
        readerValidator.validateReaderId(readerId);
        return readerService.getReader(readerId);
    }

    @RequestMapping(method = RequestMethod.GET, value = "readers")
    public List<ReaderDto> getReadersByLastName(@RequestParam String lastname) {
        return readerService.findReadersByLastname(lastname);
    }

    @RequestMapping(method = RequestMethod.POST, value = "readers", consumes = APPLICATION_JSON_VALUE)
    public void createReader(@RequestBody ReaderDto readerDto) {
        readerService.createReader(readerDto);
    }

    @RequestMapping(method = RequestMethod.PUT, value = "readers", consumes = APPLICATION_JSON_VALUE)
    public ReaderDto updateReader(@RequestBody ReaderDto readerDto) {
        readerValidator.validateReaderId(readerDto.getId());
        ReaderDto reader = readerService.getReader(readerDto.getId());
        reader.setLastName(readerDto.getLastName());
        reader.setActive(readerDto.isActive());
        return readerService.updateReader(reader);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "readers/{readerId}")
    public void deleteReader(@PathVariable Long readerId) {
        readerValidator.validateReaderId(readerId);
        readerService.deleteReader(readerId);
    }

}
