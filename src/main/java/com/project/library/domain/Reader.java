package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@NamedNativeQuery(
        name = "Reader.retrieveReadersWhereLastnameFragmentIs",
        query = "SELECT * FROM readers WHERE lower(last_name) LIKE CONCAT('%', :LASTNAME, '%')",
        resultClass = Reader.class
)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Setter
@Table(name = "readers")
public class Reader {

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "date_created")
    private LocalDate dateCreated;

    @Column(name = "mail")
    private String mail;

    @Column
    private boolean active;

    public Reader(String firstName, String lastName, LocalDate dateCreated, String mail, boolean active) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateCreated = dateCreated;
        this.mail = mail;
        this.active = active;
    }
}
