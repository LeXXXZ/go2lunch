package ru.lexxxz.go2lunch.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import ru.lexxxz.go2lunch.util.ValidationUtil;
import ru.lexxxz.go2lunch.util.exception.*;

import javax.servlet.http.HttpServletRequest;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    //  http://stackoverflow.com/a/22358422/548473
    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY) //422
    @ExceptionHandler(NotFoundException.class)
    public ErrorInfo handleNotFoundError(HttpServletRequest req, NotFoundException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(OutOfTimeException.class)
    public ErrorInfo voteTimeConflict(HttpServletRequest req, OutOfTimeException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(value = HttpStatus.UNPROCESSABLE_ENTITY)  // 422
    @ExceptionHandler({IllegalRequestDataException.class, IllegalArgumentException.class, MethodArgumentTypeMismatchException.class, HttpMessageNotReadableException.class})
    public ErrorInfo illegalRequestDataError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND)  // 404
    @ExceptionHandler(ResourceNotFoundException.class)
    public ErrorInfo resourceNotFoundError(Exception e) {
        return new ErrorInfo("", e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.FORBIDDEN)  // 403
    @ExceptionHandler(ForbiddenException.class)
    public ErrorInfo ForbiddenError(Exception e) {
        return new ErrorInfo("", e.getMessage());
    }

    @ResponseStatus(value = HttpStatus.UNAUTHORIZED)  // 401
    @ExceptionHandler(UnAuthorisedException.class)
    public ErrorInfo unAuthorisedError(Exception e) {
        return new ErrorInfo("", e.getMessage());
    }

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