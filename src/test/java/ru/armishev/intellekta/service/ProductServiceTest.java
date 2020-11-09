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
import ru.armishev.intellekta.exceptions.EntityAlreadyExistException;
import ru.armishev.intellekta.exceptions.EntityHasDetailException;
import ru.armishev.intellekta.exceptions.EntityIllegalArgumentException;
import ru.armishev.intellekta.exceptions.EntityNotFoundException;

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

        Assert.assertEquals(3, products.size());
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest(){
        productService.findById(null);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNotIntegerIdTest(){
        productService.findById("123String");
    };

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFindProductTest(){
        productService.findById(123);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        productService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdProductException() {
        Product product = new Product(0, "test");

        productService.create(product);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void createExistProductException() {
        Product product = new Product(1, "car_test");

        productService.create(product);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistProductException() {
        productService.delete(123);
    }

    @Test(expected = EntityHasDetailException.class)
    public void deleteProductHasDetailExceptionException() {
        productService.delete(2);
    }

}
