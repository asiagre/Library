package com.project.library.service;

import com.project.library.domain.ReaderDto;
import com.project.library.mapper.ReaderMapper;
import com.project.library.repository.ReaderDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderService {

    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private ReaderMapper readerMapper;

    public ReaderDto createReader(final ReaderDto readerDto) {
        return readerMapper.mapToReaderDto(readerDao.save(readerMapper.mapToReader(readerDto)));
    }

    public ReaderDto updateReader(final ReaderDto readerDto) {
        return readerMapper.mapToReaderDto(readerDao.save(readerMapper.mapToReader(readerDto)));
    }

    public List<ReaderDto> findReadersByLastname(final String lastname) {
        return readerMapper.mapToReaderDtoList(readerDao.retrieveReadersWhereLastnameFragmentIs(lastname));
    }

    public ReaderDto getReader(Long readerId) {
        return readerMapper.mapToReaderDto(readerDao.findById(readerId).get());
    }

    public void deleteReader(Long readerId) {
        readerDao.removeById(readerId);
    }

    public boolean isReaderExist(long id) {
        return readerDao.existsById(id);
    }
}
