package com.project.library;

import com.project.library.domain.*;
import com.project.library.mapper.RentalMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.ReaderDao;
import com.project.library.repository.RentalDao;
import com.project.library.repository.TitleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class LibraryApplicationTests {
    @Autowired
    private ReaderDao readerDao;

    @Autowired
    private TitleDao titleDao;

    @Autowired
    private CopyDao copyDao;

    @Autowired
    private RentalDao rentalDao;

    @Autowired
    private RentalMapper rentalMapper;

    @Test
    public void shouldSaveReader() {
        //Given
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now());

        //When
        readerDao.save(reader);
        Optional<Reader> readerFromDb = readerDao.findByLastName("Kowalski");

        //Then
        Assert.assertNotEquals(null, readerFromDb);
        Assert.assertNotEquals("O", readerFromDb.get().getId());

        //CleanUp
        readerDao.delete(readerFromDb.get().getId());
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
    public void shouldCreateRental() {
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now());
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now());

        //When
        rentalDao.save(rental);
        Optional<Rental> rentalFromDb = rentalDao.findByReader(reader);

        //Then
        Assert.assertNotEquals("0", rentalFromDb.get().getId());

        //CleanUp
        rentalDao.delete(rentalFromDb.get().getId());
    }

    @Test
    public void shouldReturnBook() {
        Reader reader = new Reader("Jan", "Kowalski", LocalDate.now());
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        Rental rental = new Rental(copy, reader, LocalDate.now());
        rentalDao.save(rental);
        RentalDto rentalDto = rentalMapper.mapToRentalDto(rental);

        //When
        rentalDto.setReturnDate(LocalDate.now());
        rentalDao.save(rentalMapper.mapToRental(rentalDto));
        Optional<Rental> rentalFromDb = rentalDao.findByReader(reader);

        //Then
        Assert.assertNotEquals(null, rentalFromDb.get().getReturnDate());

        //CleanUp
        rentalDao.delete(rentalFromDb.get().getId());
    }

}
