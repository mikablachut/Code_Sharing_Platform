package com.codeSharingPlatform.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class CodeNotFoundException extends RuntimeException {
    public CodeNotFoundException() {
        super("Code not found!");
    }
}
