package com.beolnix.marvin.statistics.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class NotFound extends RuntimeException {
    public NotFound(String message) {
        super(message);
    }

    public NotFound() {
        super();
    }
}
