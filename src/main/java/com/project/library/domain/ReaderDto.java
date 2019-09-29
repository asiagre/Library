package com.project.library.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ReaderDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String mail;

    //@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate dateCreated;
    private boolean active;

    public ReaderDto(String firstName, String lastName, String mail) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mail = mail;
        this.dateCreated = LocalDate.now();
        this.active = true;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
