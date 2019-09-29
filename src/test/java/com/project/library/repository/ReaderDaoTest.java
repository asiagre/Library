package com.project.library.repository;

import com.project.library.domain.Reader;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ReaderDaoTest {

    @Autowired
    private ReaderDao readerDao;

    @Test
    public void shouldSaveReader() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);

        //When
        readerDao.save(reader);
        List<Reader> readerFromDb = readerDao.retrieveReadersWhereLastnameFragmentIs("Kowalski");

        //Then
        Assert.assertNotEquals(null, readerFromDb);
        Assert.assertNotEquals("O", readerFromDb.get(0).getId());

        //CleanUp
        readerDao.removeById(readerFromDb.get(0).getId());
    }

    @Test
    public void shouldGetReaderByLastname() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com", true);

        //When
        readerDao.save(reader);
        List<Reader> readerFromDb = readerDao.retrieveReadersWhereLastnameFragmentIs("walski");

        //Then
        Assert.assertNotEquals(null, readerFromDb);
        Assert.assertNotEquals("O", readerFromDb.get(0).getId());

        //CleanUp
        readerDao.removeById(readerFromDb.get(0).getId());
    }

    @Test
    public void shouldNotGetReaderByLastname() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);

        //When
        readerDao.save(reader);
        List<Reader> readerFromDb = readerDao.retrieveReadersWhereLastnameFragmentIs("abc");

        //Then
        Assert.assertEquals(new ArrayList<>(), readerFromDb);

        //CleanUp
        readerDao.removeById(reader.getId());
    }

    @Test
    public void shouldGetReaderById() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        readerDao.save(reader);

        //When
        List<Reader> readers = readerDao.findAll();
        Reader readerFromDb = readerDao.findById(1L).get();

        //Then
        Assert.assertEquals("Kowalski", readerFromDb.getLastName());

        //CleanUp
        readerDao.removeById(1L);
    }

    @Test
    public void shouldUpdateReader() {
        //Given
        Reader reader = new Reader("Ewa", "Kowal", LocalDate.now(), "jan.kowalski@mail.com",true);
        readerDao.save(reader);
        Long id = readerDao.retrieveReadersWhereLastnameFragmentIs("Kowal").get(0).getId();

        //When
        reader.setActive(false);
        readerDao.save(reader);
        Reader readerFromDb = readerDao.findById(id).get();

        //Then
        Assert.assertFalse(readerFromDb.isActive());

        //CleanUp
        readerDao.removeById(id);
    }

    @Test
    public void shouldDeleteReader() {
        //Given
        Reader reader1 = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);
        Reader reader2 = new Reader("Anna", "Nowak", LocalDate.now(), "anna.nowak@mail.com",true);
        readerDao.save(reader1);
        readerDao.save(reader2);

        //When
        List<Reader> readersBeforeRemoving = readerDao.findAll();
        Long id = readersBeforeRemoving.get(1).getId();
        readerDao.removeById(id);
        List<Reader> readersAfterRemoving = readerDao.findAll();

        //Then
        Assert.assertEquals(2, readersBeforeRemoving.size());
        Assert.assertEquals(1, readersAfterRemoving.size());

        //CleanUp
        readerDao.removeById(readersAfterRemoving.get(0).getId());
    }

    @Test
    public void shouldFindReaderIfExist() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now(), "jan.kowalski@mail.com",true);

        //When
        readerDao.save(reader);
        Long id1 = readerDao.retrieveReadersWhereLastnameFragmentIs("Kowal").get(0).getId();
        boolean exist1 = readerDao.existsById(id1);
        boolean exist2 = readerDao.existsById(200L);

        //Then
        Assert.assertTrue(exist1);
        Assert.assertFalse(exist2);

        //CleanUp
        readerDao.removeById(id1);
    }

}