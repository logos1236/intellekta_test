package ru.armishev.intellekta.service;

import org.checkerframework.checker.units.qual.A;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.armishev.intellekta.config.TestConfig;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.exceptions.EntityIllegalArgumentException;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class ProductServiceTest {
    @Autowired
    private ProductService productService;

    @Test
    public void findAllTest(){
        List<Product> products = productService.findAll();

        Assert.assertEquals(2, products.size());
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        productService.create(null);

    }
}
