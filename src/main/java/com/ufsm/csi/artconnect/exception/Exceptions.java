package com.ufsm.csi.artconnect.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class Exceptions {
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e) {
        return "errorPage";
    }
}
