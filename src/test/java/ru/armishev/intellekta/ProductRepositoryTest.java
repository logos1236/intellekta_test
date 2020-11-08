package ru.armishev.intellekta;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.jpa.ProductJpaRepository;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class ProductRepositoryTest {
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Before
    public void createProductTest() {
        Product product = new Product(3, "product_test");
        productJpaRepository.save(product);
    }

    @Test
    public void findAllTest() {
        List<Product> products = productJpaRepository.findAll();
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }

    @Test
    public void findByName() {
        List<Product> products = productJpaRepository.findByName("product_test");
        Assert.assertNotNull(products);
        Assert.assertEquals(products.size(), 1);
    }

    @After
    public void deleteProduct() {
        productJpaRepository.deleteById(3);

        List<Product> products = productJpaRepository.findAll();
        Assert.assertNotNull(products);
        Assert.assertEquals(2, products.size());
    }
}
