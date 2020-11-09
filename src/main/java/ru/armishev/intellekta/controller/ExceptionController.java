package ru.armishev.intellekta.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.armishev.intellekta.entity.ErrorResponseEntity;
import ru.armishev.intellekta.exceptions.*;

@ControllerAdvice
public class ExceptionController {
    private static ErrorResponseEntity createErrorResponseEntity(BaseException e, HttpStatus httpStatus) {
        return new ErrorResponseEntity(e.getMessage(), httpStatus.getReasonPhrase(), httpStatus.value());
    }

    @ExceptionHandler(EntityIllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorResponseEntity handlerEntityIllegalArgumentException(EntityIllegalArgumentException e) {
        return createErrorResponseEntity(e, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseEntity handlerEntityAlreadyExistException(EntityAlreadyExistException e) {
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityConflictException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseEntity handlerEntityConflictException(EntityConflictException e) {
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityHasDetailException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    @ResponseBody
    public ErrorResponseEntity handlerEntityHasDetailException(EntityHasDetailException e) {
        return createErrorResponseEntity(e, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseEntity handlerEntityNotFoundException(EntityNotFoundException e) {
        return createErrorResponseEntity(e, HttpStatus.NOT_FOUND);
    }
}
