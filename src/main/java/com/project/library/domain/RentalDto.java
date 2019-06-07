package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class RentalDto {
    private Long id;
    private Reader reader;
    private Copy copy;
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public RentalDto(Reader reader, Copy copy, LocalDate borrowDate) {
        this.reader = reader;
        this.copy = copy;
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
