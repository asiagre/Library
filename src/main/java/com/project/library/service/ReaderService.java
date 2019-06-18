package com.project.library.service;

import com.project.library.domain.ReaderDto;
import com.project.library.mapper.ReaderMapper;
import com.project.library.repository.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderService {

    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private ReaderMapper readerMapper;

    public ReaderDto createReader(final ReaderDto readerDto) {
        ReaderDto reader = new ReaderDto(readerDto.getFirstName(), readerDto.getLastName());
        return readerMapper.mapToReaderDto(readerDao.save(readerMapper.mapToReader(reader)));
    }

    public boolean isReaderExist(long id) {
        return readerDao.existsById(id);
    }
}
