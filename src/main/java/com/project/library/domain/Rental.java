package com.project.library.domain;

import com.sun.javafx.beans.IDProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "rentals")
public class Rental {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "copy_id")
    private Copy copy;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "reader_id")
    private Reader reader;

    @Column(name = "borrow_date")
    private LocalDate borrowDate;

    @Column(name = "return_date")
    private LocalDate returnDate;

    public Rental(Copy copy, Reader reader, LocalDate borrowDate, LocalDate returnDate) {
        this.copy = copy;
        this.reader = reader;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Rental(Copy copy, Reader reader, LocalDate borrowDate) {
        this.copy = copy;
        this.reader = reader;
        this.borrowDate = borrowDate;
    }
}
