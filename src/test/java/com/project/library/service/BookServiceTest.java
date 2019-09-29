package com.project.library.service;

import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.TitleMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.TitleDao;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Matchers.booleanThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private TitleDao titleDao;

    @Mock
    private CopyDao copyDao;

    @Mock
    private TitleMapper titleMapper;

    @Mock
    private CopyMapper copyMapper;

    @Test
    public void shouldGetAllBooks() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        TitleDto titleDto = new TitleDto(1L, "The Lord of The Rings", "J.R.R. Tolkien", 1954);
        List<Title> listOfBooks = new ArrayList<>();
        listOfBooks.add(title);
        List<TitleDto> listOfBooksDto = new ArrayList<>();
        listOfBooksDto.add(titleDto);
        when(titleDao.save(title)).thenReturn(title);
        when(titleDao.findAll()).thenReturn(listOfBooks);
        when(titleMapper.mapToTitleDtoList(listOfBooks)).thenReturn(listOfBooksDto);

        //When
        List<TitleDto> titleDtos = bookService.getBooks();

        //Then
        Assert.assertEquals(1, titleDtos.size());
        Assert.assertEquals("The Lord of The Rings", titleDtos.get(0).getBookTitle());
    }

    @Test
    public void shouldAddBook() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        TitleDto titleDto = new TitleDto(1L, "The Lord of The Rings", "J.R.R. Tolkien", 1954);
        List<Title> listOfBooks = new ArrayList<>();
        listOfBooks.add(title);
        List<TitleDto> listOfBooksDto = new ArrayList<>();
        listOfBooksDto.add(titleDto);
        when(titleDao.save(title)).thenReturn(title);
        when(titleMapper.mapToTitleDto(title)).thenReturn(titleDto);
        when(titleMapper.mapToTitle(titleDto)).thenReturn(title);

        //When
        TitleDto titleDtoFromDb = bookService.addBook(titleDto);

        //Then
        Assert.assertEquals("The Lord of The Rings", titleDtoFromDb.getBookTitle());
    }

    @Test
    public void shouldAddCopy() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        CopyDto copyDto = new CopyDto(1L, State.PREOWNED);
        when(titleDao.findOne(1L)).thenReturn(title);
        when(copyMapper.mapToCopy(title, copyDto)).thenReturn(copy);
        when(copyDao.save(copy)).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);
        when(copyDao.findById(1l)).thenReturn(Optional.of(copy));

        //When
        bookService.addCopy(1L, copyDto);
        Copy copyFromDb = bookService.getCopy(1l).get();

        //Then
        Assert.assertEquals(State.PREOWNED, copyFromDb.getState());
    }

    @Test
    public void shouldCountCopies() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        title.getCopies().add(new Copy(title, State.PREOWNED));
        title.getCopies().add(new Copy(title, State.BORROWED));
        title.getCopies().add(new Copy(title, State.PREOWNED));
        title.getCopies().add(new Copy(title, State.DESTROYED));
        when(titleDao.findOne(1L)).thenReturn(title);

        //When
        int numberOfCopies = bookService.howManyCopiesAvailable(1L);

        //Then
        Assert.assertEquals(2, numberOfCopies);
    }

    @Test
    public void shouldChangeState() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        CopyDto copyDto = new CopyDto(1L, State.PREOWNED);
        when(copyDao.save(copy)).thenReturn(copy);
        when(copyMapper.mapToCopyDto(copy)).thenReturn(copyDto);
        when(copyDao.findOne(1l)).thenReturn(copy);
        when(copyDao.findById(1L)).thenReturn(Optional.of(copy));

        //When
        bookService.changeState(1L, State.BORROWED);
        Copy copyFromDb = bookService.getCopy(1l).get();

        //Then
        Assert.assertEquals(State.BORROWED, copyFromDb.getState());
    }

    @Test
    public void shouldGetCopy() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        Copy copy = new Copy(title, State.PREOWNED);
        when(copyDao.findById(1l)).thenReturn(Optional.of(copy));

        //When
        Copy copyFromDb = bookService.getCopy(1l).get();

        //Then
        Assert.assertEquals(State.PREOWNED, copyFromDb.getState());
    }

    @Test
    public void shouldFindBooksByTitle() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        TitleDto titleDto = new TitleDto(1L, "The Lord of The Rings", "J.R.R. Tolkien", 1954);
        List<Title> listOfBooks = new ArrayList<>();
        listOfBooks.add(title);
        List<TitleDto> listOfBooksDto = new ArrayList<>();
        listOfBooksDto.add(titleDto);
        when(titleDao.retrieveBookByTitle("Lord")).thenReturn(listOfBooks);
        when(titleMapper.mapToTitleDtoList(listOfBooks)).thenReturn(listOfBooksDto);

        //When
        List<TitleDto> titleDtos = bookService.findBooksByTitle("Lord");

        //Then
        Assert.assertEquals(1, titleDtos.size());
        Assert.assertEquals("The Lord of The Rings", titleDtos.get(0).getBookTitle());
    }

    @Test
    public void shouldFindBooksByAuthor() {
        //Given
        Title title = new Title("The Lord of The Rings", "J.R.R. Tolkien", 1954);
        TitleDto titleDto = new TitleDto(1L, "The Lord of The Rings", "J.R.R. Tolkien", 1954);
        List<Title> listOfBooks = new ArrayList<>();
        listOfBooks.add(title);
        List<TitleDto> listOfBooksDto = new ArrayList<>();
        listOfBooksDto.add(titleDto);
        when(titleDao.retrieveBookByAuthor("Tolk")).thenReturn(listOfBooks);
        when(titleMapper.mapToTitleDtoList(listOfBooks)).thenReturn(listOfBooksDto);

        //When
        List<TitleDto> titleDtos = bookService.findBooksByAuthor("Tolk");

        //Then
        Assert.assertEquals(1, titleDtos.size());
        Assert.assertEquals("J.R.R. Tolkien", titleDtos.get(0).getAuthor());
    }

    @Test
    public void shouldDeleteBook() {
        //Given&When
        bookService.deleteBook(1L);

        //Then
        verify(titleDao, times(1)).delete(1L);
    }

    @Test
    public void shouldCheckIfBookExist() {
        //Given
        when(titleDao.existsById(1L)).thenReturn(true);
        when(titleDao.existsById(2L)).thenReturn(false);

        //When
        boolean exists1 = bookService.isBookExist(1L);
        boolean exists2 = bookService.isBookExist(2L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);
    }

    @Test
    public void shouldCheckIfCopyExist() {
        //Given
        when(copyDao.existsById(1L)).thenReturn(true);
        when(copyDao.existsById(2L)).thenReturn(false);

        //When
        boolean exists1 = bookService.isCopyExist(1L);
        boolean exists2 = bookService.isCopyExist(2L);

        //Then
        Assert.assertTrue(exists1);
        Assert.assertFalse(exists2);
    }
}