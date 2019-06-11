package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CopyDto {
    private Long id;
    private State state;

    public CopyDto(State state) {
        this.state = state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
