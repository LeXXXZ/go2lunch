package ru.lexxxz.go2lunch.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.lexxxz.go2lunch.util.exception.ForbiddenException;
import ru.lexxxz.go2lunch.util.exception.ResourceNotFoundException;
import ru.lexxxz.go2lunch.util.exception.UnAuthorisedException;


@RestController
@RequestMapping(ErrorController.ERROR_URL)
public class ErrorController {

    static final String ERROR_URL = "/errors";

    @RequestMapping("/resource-not-found")
    public void resourceNotFound() {
        throw  new ResourceNotFoundException("ERROR 404 : Resource Not Found");
    }

    @RequestMapping("/forbidden")
    public void forbidden() {
        throw new ForbiddenException("ERROR 403 : Access is forbidden");
    }

    @RequestMapping("/unauthorised")
    public void unAuthorised() {
        throw  new UnAuthorisedException("ERROR 401 : Unauthorised Request");
    }
}