package org.example.devicesweb.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public class WebExceptionHandler {
    @ExceptionHandler(UnAuthorizedAccessToken.class)
    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)
    public ModelAndView handleInvalidStateException(HttpServletRequest req, UnAuthorizedAccessToken e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public ModelAndView handleRuntimeException(HttpServletRequest req, RuntimeException e) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("exception", e.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
