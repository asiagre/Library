package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TitleDto {
    private Long id;
    private String bookTitle;
    private String author;
    private int publishedYear;
    private List<CopyDto> copyDtos = new ArrayList<>();

    public TitleDto(Long id, String bookTitle, String author, int publishedYear) {
        this.id = id;
        this.bookTitle = bookTitle;
        this.author = author;
        this.publishedYear = publishedYear;
    }
}
