package com.project.library.mapper;

import com.project.library.domain.Title;
import com.project.library.domain.TitleDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TitleMapper {

    public Title mapToTitle(final TitleDto titleDto) {
        return new Title(titleDto.getBookTitle(), titleDto.getAuthor(), titleDto.getPublishedYear());
    }

    public TitleDto mapToTitleDto(final Title title) {
        return new TitleDto(title.getId(), title.getBookTitle(), title.getAuthor(), title.getPublishedYear());
    }

    public List<TitleDto> mapToTitleDtoList(final List<Title> titleList) {
        return titleList.stream()
                .map(title -> mapToTitleDto(title))
                .collect(Collectors.toList());
    }
}
