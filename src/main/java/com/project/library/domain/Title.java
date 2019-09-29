package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQueries({
        @NamedNativeQuery(
                name = "Title.retrieveBookByTitle",
                query = "SELECT * FROM titles WHERE lower(book_title) LIKE CONCAT('%', :TITLE, '%')",
                resultClass = Title.class
        ),
        @NamedNativeQuery(
                name = "Title.retrieveBookByAuthor",
                query = "SELECT * FROM titles WHERE lower(author) LIKE CONCAT('%', :AUTHOR, '%')",
                resultClass = Title.class
        )
})
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name = "titles")
public class Title {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "author")
    private String author;

    @Column(name = "published_year")
    private int publishedYear;

    @OneToMany(
            targetEntity = Copy.class,
            mappedBy = "title",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    private List<Copy> copies = new ArrayList<>();

    public Title(String bookTitle, String author, int publishedYear) {
        this.bookTitle = bookTitle;
        this.author = author;
        this.publishedYear = publishedYear;
    }
}
