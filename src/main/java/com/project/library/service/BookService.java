package com.project.library.service;

import com.project.library.domain.*;
import com.project.library.mapper.CopyMapper;
import com.project.library.mapper.TitleMapper;
import com.project.library.repository.CopyDao;
import com.project.library.repository.TitleDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public CopyDto addCopy(Long id, CopyDto copyDto) {
        Title title = titleDao.findOne(id);
        Copy copy = copyMapper.mapToCopy(title, copyDto);
        return copyMapper.mapToCopyDto(copyDao.save(copy));
    }

    public CopyDto changeState(Long copyId, State state) {
        Copy copy = copyDao.findOne(copyId);
        copy.setState(state);
        return copyMapper.mapToCopyDto(copyDao.save(copy));
    }

    public int howManyCopiesAvailable(Long titleId) {
        Title title = titleDao.findOne(titleId);
        List<Copy> availableCopies = title.getCopies().stream()
                .filter(copy -> copy.getState().equals(State.PREOWNED))
                .collect(Collectors.toList());
        return availableCopies.size();
    }

    public Optional<Copy> getCopy(final Long id) {
        return copyDao.findById(id);
    }

    public boolean isBookExist(final Long id) {
        return titleDao.existsById(id);
    }

    public boolean isCopyExist(final Long id) {
        return copyDao.existsById(id);
    }
}
