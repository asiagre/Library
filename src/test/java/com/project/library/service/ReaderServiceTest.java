package com.project.library.service;

import com.project.library.domain.Reader;
import com.project.library.domain.ReaderDto;
import com.project.library.mapper.ReaderMapper;
import com.project.library.repository.ReaderDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ReaderServiceTest {

    @InjectMocks
    private ReaderService readerService;

    @Mock
    private ReaderDao readerDao;

    @Mock
    private ReaderMapper readerMapper;

    @Test
    public void shouldCreateReader() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        ReaderDto readerDto = new ReaderDto("Jan", "Kowalski", "jan.kowalski@mail.com");
        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);
        when(readerDao.save(reader)).thenReturn(reader);

        //When
        ReaderDto readerFromDb = readerService.createReader(readerDto);//readerService.getReader(1L);

        //Then
        Assert.assertNotEquals(null, readerFromDb);
        Assert.assertEquals("Jan", readerFromDb.getFirstName());
    }

    @Test
    public void updateReader() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        ReaderDto readerDto = new ReaderDto("Jan", "Kowalski", "jan.kowalski@mail.com");
        when(readerMapper.mapToReader(readerDto)).thenReturn(reader);
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);
        when(readerDao.save(reader)).thenReturn(reader);

        //When
        ReaderDto readerFromDb = readerService.updateReader(readerDto);

        //Then
        Assert.assertNotEquals(null, readerFromDb);
        Assert.assertEquals("Jan", readerFromDb.getFirstName());
    }

    @Test
    public void findReadersByLastname() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        ReaderDto readerDto = new ReaderDto("Jan", "Kowalski", "jan.kowalski@mail.com");
        List<Reader> readers = new ArrayList<>();
        readers.add(reader);
        List<ReaderDto> readerDtos = new ArrayList<>();
        readerDtos.add(readerDto);
        when(readerMapper.mapToReaderDtoList(readers)).thenReturn(readerDtos);
        when(readerDao.retrieveReadersWhereLastnameFragmentIs("owa")).thenReturn(readers);

        //When
        List<ReaderDto> readersFromDb = readerService.findReadersByLastname("owa");

        //Then
        Assert.assertEquals(1, readersFromDb.size());
        Assert.assertEquals("Jan", readersFromDb.get(0).getFirstName());
    }

    @Test
    public void getReader() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        ReaderDto readerDto = new ReaderDto("Jan", "Kowalski", "jan.kowalski@mail.com");
        when(readerMapper.mapToReaderDto(reader)).thenReturn(readerDto);
        when(readerDao.findById(1L)).thenReturn(Optional.of(reader));

        //When
        ReaderDto readerFromDb = readerService.getReader(1L);

        //Then
        Assert.assertEquals("Kowalski", readerFromDb.getLastName());
    }

    @Test
    public void deleteReader() {
        //Given&When
        readerService.deleteReader(1L);

        //Then
        verify(readerDao, times(1)).removeById(1L);
    }

    @Test
    public void isReaderExist() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        when(readerDao.existsById(1L)).thenReturn(true);
        when(readerDao.existsById(2L)).thenReturn(false);

        //When
        boolean exists1 = readerService.isReaderExist(1L);
        boolean exists2 = readerService.isReaderExist(2L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);
    }
}