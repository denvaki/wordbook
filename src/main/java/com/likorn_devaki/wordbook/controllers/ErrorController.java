package com.likorn_devaki.wordbook.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class ErrorController {
    @ResponseBody
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handle(Exception ex) {
        System.out.println(ex.toString());
        return ex.getMessage();//this is view name
    }
}
