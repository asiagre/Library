package com.project.library.repository;

import com.project.library.domain.Copy;
import com.project.library.domain.State;
import com.project.library.domain.Title;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BookDaoTest {

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private CopyDao copyDao;

    @Test
    public void shouldGetBooks() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title);

        //When
        List<Title> titles = titleDao.findAll();

        //Then
        Assert.assertEquals(1, titles.size());
        Assert.assertEquals("The Lord of The Rings", titles.get(0).getBookTitle());

        //CleanUp
        titleDao.delete(titles.get(0).getId());
    }

    @Test
    public void shouldAddBook() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);

        //When
        titleDao.save(title);
        Optional<Title> titleFromDb = titleDao.findByBookTitle("The Lord of The Rings");

        //Then
        Assert.assertNotEquals(null, titleFromDb);
        Assert.assertNotEquals("O", titleFromDb.get().getId());

        //CleanUp
        titleDao.delete(titleFromDb.get().getId());
    }

    @Test
    public void shouldAddCopy() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);

        //When
        copyDao.save(copy);
        Optional<Copy> copyFromDb = copyDao.findByTitleAndState(title, State.PREOWNED);

        //Then
        Assert.assertNotEquals(null, copyFromDb.get());
        Assert.assertNotEquals(new Long(0), copyFromDb.get().getId());

        //CleanUp
        copyDao.delete(copyFromDb.get().getId());
    }

    @Test
    public void shouldChangeState() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        copyDao.save(copy);

        //When
        copy.setState(State.DESTROYED);
        copyDao.save(copy);
        Optional<Copy> copyFromDb = copyDao.findByTitleAndState(title, State.DESTROYED);

        //Then
        Assert.assertNotEquals(State.PREOWNED, copyFromDb.get().getState());
        Assert.assertEquals(State.DESTROYED, copyFromDb.get().getState());

        //CleanUp
        copyDao.delete(copyFromDb.get().getId());
    }

    @Test
    public void shouldCountCopies() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title);
        Copy copy = new Copy(title, State.PREOWNED);
        Copy copy1 = new Copy(title, State.DESTROYED);
        Copy copy2 = new Copy(title, State.PREOWNED);
        Copy copy3 = new Copy(title, State.BORROWED);
        Copy copy4 = new Copy(title, State.PREOWNED);
        title.getCopies().add(copy);
        title.getCopies().add(copy1);
        title.getCopies().add(copy2);
        title.getCopies().add(copy3);
        title.getCopies().add(copy4);
        titleDao.save(title);

        //When
        Optional<Title> titleFromDb = titleDao.findByBookTitle("The Lord of The Rings");
        int count = titleFromDb.get().getCopies().size();

        //Then
        Assert.assertEquals(5, count);

        //CleanUp
        titleDao.delete(titleFromDb.get().getId());
    }


    @Test
    public void shouldFindBookByTitle() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title);

        //When
        Title titleFromDb = titleDao.retrieveBookByTitle("ord").get(0);

        //Then
        Assert.assertNotEquals(null, titleFromDb);
        Assert.assertNotEquals("O", titleFromDb.getId());

        //CleanUp
        titleDao.delete(titleFromDb.getId());
    }


    @Test
    public void shouldFindBookByAuthor() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title);

        //When
        Title titleFromDb = titleDao.retrieveBookByAuthor("Tolk").get(0);

        //Then
        Assert.assertNotEquals(null, titleFromDb);
        Assert.assertNotEquals("O", titleFromDb.getId());

        //CleanUp
        titleDao.delete(titleFromDb.getId());
    }

    @Test
    public void shouldDeleteBook() {
        //Given
        Title title1 = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title1);

        //When
        List<Title> titlesBeforeRemoving = titleDao.findAll();
        Long id = titlesBeforeRemoving.get(0).getId();
        titleDao.delete(id);
        List<Title> titlesAfterRemoving = titleDao.findAll();

        //Then
        Assert.assertEquals(1, titlesBeforeRemoving.size());
        Assert.assertEquals(0, titlesAfterRemoving.size());
    }

    @Test
    public void shouldFindTitleIfExist() {
        //Given
        //Given
        Title title1 = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        titleDao.save(title1);

        //When
        Long id1 = titleDao.findByBookTitle("The Lord of The Rings").get().getId();
        boolean exist1 = titleDao.existsById(id1);
        boolean exist2 = titleDao.existsById(200L);

        //Then
        Assert.assertTrue(exist1);
        Assert.assertFalse(exist2);

        //CleanUp
        titleDao.delete(id1);
    }

    @Test
    public void shouldFindCopyIfExist() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        title.getCopies().add(copy);
        titleDao.save(title);

        //When
        Long id1 = copyDao.findByTitleAndState(title, State.PREOWNED).get().getId();
        boolean exist1 = copyDao.existsById(id1);
        boolean exist2 = copyDao.existsById(200L);

        //Then
        Assert.assertTrue(exist1);
        Assert.assertFalse(exist2);

        //CleanUp
        copyDao.delete(id1);
    }

}