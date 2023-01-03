package com.sensei.mailService;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Mail {
    private String mailTo;
    private String subject;
    private String message;
}
