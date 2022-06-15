package com.sample.billsexchange.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode(callSuper=false)
public class NoChangeLeftException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private String message;

    public NoChangeLeftException() {
        this.message = "Change not available for this bill !";
    }

    public NoChangeLeftException(Exception e) {
        super(e);
        this.message = "Change not available for this bill !";
    }
}
