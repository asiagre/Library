package com.project.library.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Mail {
    private String receiverEmail;
    private String subject;
    private String message;
}
