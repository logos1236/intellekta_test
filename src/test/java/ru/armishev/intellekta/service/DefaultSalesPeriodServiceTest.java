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
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.exceptions.*;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.service.impl.DefaultSalesPeriodService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class DefaultSalesPeriodServiceTest {
    @Autowired
    private DefaultSalesPeriodService defaultSalesPeriodService;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void findAllTest(){
        List<SalesPeriod> products = defaultSalesPeriodService.findAll();

        Assert.assertEquals(6, products.size());
    };

    @Test
    public void findByIdTest(){
        SalesPeriod salesPeriod = defaultSalesPeriodService.findById(1);
        Assert.assertEquals(1, salesPeriod.getId());
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNullTest(){
        defaultSalesPeriodService.findById(null);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void findByIdNotIntegerIdTest(){
        defaultSalesPeriodService.findById("123String");
    };

    @Test(expected = EntityNotFoundException.class)
    public void findByIdNotFindSalesPeriodServiceTest(){
        defaultSalesPeriodService.findById(-1);
    };

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullSalesPeriodException() {
        defaultSalesPeriodService.create(null);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullProductException() {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));

        SalesPeriod salesPeriod = new SalesPeriod(7, 300, dateFrom, dateTo, null);

        defaultSalesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullIdProductException() {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
        Product product = new Product(0, "test");

        SalesPeriod salesPeriod = new SalesPeriod(7, 300, dateFrom, dateTo, product);

        defaultSalesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityIllegalArgumentException.class)
    public void createNullDateFromException() {
        Date dateFrom = null;
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
        Optional<Product> optionalProduct = productJpaRepository.findById(1);
        Assert.assertTrue(optionalProduct.isPresent());

        SalesPeriod salesPeriod = new SalesPeriod(7, 300, dateFrom, dateTo, optionalProduct.get());

        defaultSalesPeriodService.create(salesPeriod);
    }

    @Test(expected = EntityAlreadyExistException.class)
    public void createExistProductException() {
        SalesPeriod existedSalesPeriod = defaultSalesPeriodService.findById(1);
        System.out.println(existedSalesPeriod);

        defaultSalesPeriodService.create(existedSalesPeriod);
    }

    @Test
    public void createSalesPeriod() {
        int salesPeriodCountStart = defaultSalesPeriodService.findAll().size();

        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));;
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
        Optional<Product> optionalProduct = productJpaRepository.findById(1);
        Assert.assertTrue(optionalProduct.isPresent());
        SalesPeriod salesPeriod = new SalesPeriod(7, 300, dateFrom, dateTo, optionalProduct.get());
        defaultSalesPeriodService.create(salesPeriod);
        int salesPeriodCountEnd = defaultSalesPeriodService.findAll().size();

        Assert.assertEquals(salesPeriodCountStart+1, salesPeriodCountEnd);
    }

    @Test(expected = EntityNotFoundException.class)
    public void deleteNotExistSalesPeriodException() {
        defaultSalesPeriodService.delete(-1);
    }

    @Test(expected = EntityConflictException.class)
    public void deleteSalesPeriodHasDetailExceptionException() {
        defaultSalesPeriodService.delete(4);
    }

    @Test
    public void deleteSalesPeriod() {
        int productsCountStart = defaultSalesPeriodService.findAll().size();
        if (productsCountStart == 0) {
            Assert.fail();
        }

        defaultSalesPeriodService.delete(5);
        int productsCountEnd = defaultSalesPeriodService.findAll().size();

        Assert.assertEquals(productsCountStart-1, productsCountEnd);
    }

}
