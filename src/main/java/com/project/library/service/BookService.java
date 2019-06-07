package com.project.library.service;

import com.project.library.domain.Copy;
import com.project.library.domain.CopyDto;
import com.project.library.domain.Title;
import com.project.library.domain.TitleDto;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.TitleMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private TitleDao titleDao;

    @Autowired
    private CopyDao copyDao;

    @Autowired
    private TitleMapper titleMapper;

    @Autowired
    private CopyMapper copyMapper;


    public TitleDto addBook(final TitleDto titleDto) {
        Title title = titleMapper.mapToTitle(titleDto);
        return titleMapper.mapToTitleDto(titleDao.save(title));
    }

    public Optional<Title> getBook(final Long id) {
        return titleDao.findById(id);
    }

    public boolean isBookExist(final Long id) {
        return titleDao.existsById(id);
    }

    public CopyDto addCopy(Long id, CopyDto copyDto) {
        Title title = titleDao.findOne(id);
        Copy copy = copyMapper.mapToCopy(title, copyDto);
        return copyMapper.mapToCopyDto(copyDao.save(copy));
    }
}
