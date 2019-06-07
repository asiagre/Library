package com.project.library;

import com.project.library.controller.LibraryController;
import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.ReaderMapper;
import com.project.library.mapper.TitleMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.ReaderDao;
import com.project.library.repository.TitleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {
    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private ReaderMapper readerMapper;

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private CopyDao copyDao;

    @Autowired
    private CopyMapper copyMapper;

    @Test
    public void shouldSaveReader() {
        //Given
        ReaderDto readerDto = new ReaderDto("Jan", "Kowalski");

        //When
        readerDao.save(readerMapper.mapToReader(readerDto));
        Optional<Reader> reader = readerDao.findByLastName("Kowalski");

        //Then
        Assert.assertNotEquals(null, reader);
        Assert.assertNotEquals("O", reader.get().getId());

//        //CleanUp
//        readerDao.delete(reader.get().getId());
    }

    @Test
    public void shouldAddBook() {
        //Given
        TitleDto titleDto = new TitleDto("The Lord of The Rings", "J.R.R. Tolkien", 1954);

        //When
        titleDao.save(titleMapper.mapToTitle(titleDto));
        Optional<Title> title = titleDao.findByBookTitle("The Lord of The Rings");

        //Then
        Assert.assertNotEquals(null, title);
        Assert.assertNotEquals("O", title.get().getId());

//        //CleanUp
//        titleDao.delete(title.get().getId());
    }

    @Test
    public void shouldAddCopy() {
        //Given
        TitleDto titleDto = new TitleDto("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Title title = titleMapper.mapToTitle(titleDto);
        titleDao.save(title);
        CopyDto copyDto = new CopyDto(titleDto, State.PREOWNED);

        //When
        copyDao.save(copyMapper.mapToCopy(copyDto));
        Optional<Copy> copy = copyDao.findById(1L);

        //Then
        Assert.assertNotEquals("O", copy.get().getId());

//        //CleanUp
//        titleDao.delete(copy.get().getId());
    }

}
