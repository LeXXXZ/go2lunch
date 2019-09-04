package ru.lexxxz.go2lunch.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.lexxxz.go2lunch.util.ValidationUtil;
import ru.lexxxz.go2lunch.util.exception.ErrorInfo;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e);
    }

    private ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e) {
        Throwable rootCause = ValidationUtil.logAndGetRootCause(log, req, e);
        return new ErrorInfo(req.getRequestURL(), rootCause.getMessage());
    }
}