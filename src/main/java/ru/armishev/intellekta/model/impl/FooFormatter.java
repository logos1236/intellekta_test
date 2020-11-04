package ru.armishev.intellekta.model.impl;

import org.springframework.stereotype.Component;
import ru.armishev.intellekta.model.Formatter;

@Component("fooFormatter")
public class FooFormatter implements Formatter {
    @Override
    public String format() {
        return "bar";
    }
}
