package ru.armishev.intellekta.exceptions;

import org.springframework.util.Assert;

/*
* ключение выбрасывается при поворном создании сущности с заданным ключом
 */
public class EntityAlreadyExistException extends BaseException {
    public EntityAlreadyExistException(String message) {
        super(message);
    }

    public EntityAlreadyExistException(String type, Object id) {
        this(formatMessage(type, id));
    }

    private static String formatMessage(String type, Object id) {
        Assert.hasText(type, "Тип не может быть пустым");
        Assert.notNull(id, "Идентификатор объекта не может быть null");
        Assert.hasText(id.toString(), "Идентификатор объекта не может быть пустым");

        return String.format("%s с ключом %s уже существует", type, id);
    }
}
