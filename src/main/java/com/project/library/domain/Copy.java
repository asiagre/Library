package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "copies")
public class Copy {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    private Title title;

    @Column(name = "state")
    private State state;

    public Copy(Title title, State state) {
        this.title = title;
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }

}
