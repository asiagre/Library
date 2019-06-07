package com.project.library.service;

import com.project.library.domain.ReaderDto;
import com.project.library.mapper.ReaderMapper;
import com.project.library.repository.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private ReaderMapper readerMapper;

    public ReaderDto createReader(final ReaderDto readerDto) {
        ReaderDto user = new ReaderDto(readerDto.getFirstName(), readerDto.getLastName());
        return readerMapper.mapToReaderDto(readerDao.save(readerMapper.mapToReader(user)));
    }
}
