package ru.armishev.intellekta.model.impl;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;
import ru.armishev.intellekta.model.Formatter;

@Component("barFormatter")
@Primary
public class BarFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
