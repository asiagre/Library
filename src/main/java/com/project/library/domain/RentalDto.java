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

    public RentalDto(Long readerId, Long copyId) {
        this.readerId = readerId;
        this.copyId = copyId;
        this.borrowDate = LocalDate.now();
        this.returnDate = null;
    }

    public RentalDto(Long id, Long readerId, Long copyId, LocalDate borrowDate) {
        this.id = id;
        this.readerId = readerId;
        this.copyId = copyId;
        this.borrowDate = borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }
}
