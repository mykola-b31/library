package ua.cn.stu.library.generics.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public String handleNotFoundException(NotFoundException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(InvalidRequestMethodException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public String handleInvalidRequestMethodException(InvalidRequestMethodException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ResponseBody
    public String handleInvalidRequestMethodException(HttpMediaTypeNotSupportedException ex) {
        return ex.getMessage();
    }

    @ExceptionHandler(
            {IllegalStateException.class, IllegalArgumentException.class}
    )
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleBussinessExceptions(RuntimeException ex) {
        return "{\"message\": \"" + ex.getMessage() + "\"}";
    }

}
