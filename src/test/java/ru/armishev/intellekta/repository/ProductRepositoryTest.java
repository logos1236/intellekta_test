package ru.armishev.intellekta.repository;

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
import ru.armishev.intellekta.jpa.ProductJpaRepository;

import java.util.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class ProductRepositoryTest {
    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void createProductTest() {
        Product product = new Product(3, "product_test");
        productJpaRepository.save(product);

        Optional<Product> optionalProduct = productJpaRepository.findById(3);
        Assert.assertTrue(optionalProduct.isPresent());
    }

    @Test
    public void createProductTestRandomIdTest() {
        try {
            Product product = new Product(7, "product_test");
            productJpaRepository.save(product);
            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void findAllProductTest() {
        List<Product> products = productJpaRepository.findAll();
        Assert.assertNotNull(products);
        Assert.assertEquals(3, products.size());
    }

    @Test
    public void deleteProductTest() {
        long start_count = productJpaRepository.count();

        if (start_count > 0) {
            productJpaRepository.deleteById(1);
            long end_count = productJpaRepository.count();

            Assert.assertEquals(start_count-1, end_count);
        } else {
            Assert.assertEquals(start_count, 0);
        }
    }

    @Test
    public void deleteAllProductTest() {
        Assert.assertTrue(productJpaRepository.count() > 0);

        productJpaRepository.deleteAll();
        Assert.assertEquals(productJpaRepository.count(), 0);
    }

    @Test
    public void deleteProductByObjectTest() {
        Product product = new Product(3, "bike_test");
        productJpaRepository.delete(product);

        List<Product> products = productJpaRepository.findByName("bike_test");
        Assert.assertEquals(0, products.size());
    }

    @Test
    public void findAllProductByNameTest() {
        List<Product> products = productJpaRepository.findByName("car_test");
        Assert.assertNotNull(products);
        Assert.assertEquals(2, products.size());
    }

    @Test
    public void findProductByNameTest() {
        List<Product> products = productJpaRepository.findByName("car_test");
        Assert.assertNotNull(products);
        Assert.assertEquals(products.get(0).getName(), "car_test");
    }

    @Test
    public void findProductByNameNegativeTest() {
        List<Product> products = productJpaRepository.findByName("car_test_1");
        Assert.assertNotNull(products);
        Assert.assertEquals(products.size(), 0);
    }

    @Test
    public void findProductByIdTest() {
        Optional<Product> optionalProduct = productJpaRepository.findById(1);
        Assert.assertTrue(optionalProduct.isPresent());
        Assert.assertEquals(optionalProduct.get().getId(), 1);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<Product> optionalProduct = productJpaRepository.findById(-1);
        Assert.assertFalse(optionalProduct.isPresent());
    }
}
