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
    private Long readerId;
    private Long copyId;
    private LocalDate borrowDate;
    private LocalDate returnDate;
    private boolean returned = false;

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        returned = true;
    }
}
