package ru.lexxxz.go2lunch.util;

import org.slf4j.Logger;
import org.springframework.util.Assert;
import ru.lexxxz.go2lunch.HasId;
import ru.lexxxz.go2lunch.util.exception.IllegalRequestDataException;
import ru.lexxxz.go2lunch.util.exception.NotFoundException;

import javax.servlet.http.HttpServletRequest;

public class ValidationUtil {

    private ValidationUtil() {
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> void assertNotNullEntity(T entity) throws IllegalArgumentException{
        Assert.notNull(entity, "Entity must not be null");
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean + " must be with id=" + id);
        }
    }
    //  http://stackoverflow.com/a/28565320/548473
    public static Throwable getRootCause(Throwable t) {
        Throwable result = t;
        Throwable cause;

        while (null != (cause = result.getCause()) && (result != cause)) {
            result = cause;
        }
        return result;
    }

    public static String getMessage(Throwable e) {
        return e.getMessage() != null ? e.getMessage() : e.getClass().getName();
    }

    public static Throwable logAndGetRootCause(Logger log, HttpServletRequest req, Exception e) {
        Throwable rootCause = ValidationUtil.getRootCause(e);
            log.error("Error at request " + req.getRequestURL(), rootCause);
        return rootCause;
    }

}