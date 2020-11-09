package ru.armishev.intellekta.service;

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
import ru.armishev.intellekta.service.impl.DefaultProductService;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class DefaultProductServiceTest {
    @Autowired
    private DefaultProductService defaultProductService;

    @Test
    public void findAllTest(){
        List<Product> products = defaultProductService.findAll();

        Assert.assertEquals(3, products.size());
    };

    @Test
    public void findByIdTest(){
        Product product = defaultProductService.findById(1);
        Assert.assertEquals(1, product.getId());
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest(){
        defaultProductService.findById(null);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNotIntegerIdTest(){
        defaultProductService.findById("123String");
    };

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFindProductTest(){
        defaultProductService.findById(-1);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        defaultProductService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdProductException() {
        Product product = new Product(0, "test");

        defaultProductService.create(product);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void createExistProductException() {
        Product product = new Product(1, "car_test");

        defaultProductService.create(product);
    }

    @Test
    public void createProduct() {
        int productsCountStart = defaultProductService.findAll().size();

        Product product = new Product(4, "Test");
        defaultProductService.create(product);
        int productsCountEnd = defaultProductService.findAll().size();

        Assert.assertEquals(productsCountStart+1, productsCountEnd);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistProductException() {
        defaultProductService.delete(-1);
    }

    @Test(expected = EntityHasDetailException.class)
    public void deleteProductHasDetailExceptionException() {
        defaultProductService.delete(2);
    }

    @Test
    public void deleteProduct() {
        int productsCountStart = defaultProductService.findAll().size();
        if (productsCountStart == 0) {
            Assert.fail();
        }

        defaultProductService.delete(3);
        int productsCountEnd = defaultProductService.findAll().size();

        Assert.assertEquals(productsCountStart-1, productsCountEnd);
    }

}
