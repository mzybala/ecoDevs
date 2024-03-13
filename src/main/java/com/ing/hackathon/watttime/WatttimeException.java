package com.ing.hackathon.watttime;

public class WatttimeException extends RuntimeException {

    public WatttimeException(final String message, final Throwable couse) {
        super(message, couse);
    }
}
