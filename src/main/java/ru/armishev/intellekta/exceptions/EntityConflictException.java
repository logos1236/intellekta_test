package ru.armishev.intellekta.exceptions;

/*
Исключение выбрасывается при конфликтах с существующими данными
*/
public class EntityConflictException extends BaseException {
    public EntityConflictException(String message) {
        super(message);
    }
}
