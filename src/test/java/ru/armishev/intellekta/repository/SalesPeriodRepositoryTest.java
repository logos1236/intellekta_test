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
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes = TestConfig.class)
@Sql({"/schema.sql", "/data.sql"})
public class SalesPeriodRepositoryTest {
    @Autowired
    private SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Test
    public void createTest() {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
        Optional<Product> optionalProduct = productJpaRepository.findById(1);
        Assert.assertTrue(optionalProduct.isPresent());

        SalesPeriod salesPeriod = new SalesPeriod(7, 300, dateFrom, dateTo, optionalProduct.get());
        salesPeriodJpaRepository.save(salesPeriod);

        Optional<SalesPeriod> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(7L);
        Assert.assertTrue(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void createTestRandomIdTest() {
        try {
            Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));
            Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
            Optional<Product> optionalProduct = productJpaRepository.findById(1);
            Assert.assertTrue(optionalProduct.isPresent());

            SalesPeriod salesPeriod = new SalesPeriod(71111, 300, dateFrom, dateTo, optionalProduct.get());
            salesPeriodJpaRepository.save(salesPeriod);

            Assert.assertTrue(true);
        } catch (Exception e) {
            Assert.assertTrue(false);
        }
    }

    @Test
    public void findAllTest() {
        Assert.assertEquals(6, salesPeriodJpaRepository.count());
    }

    @Test
    public void deleteTest() {
        long start_count = salesPeriodJpaRepository.count();

        if (start_count > 0) {
            salesPeriodJpaRepository.deleteById(1L);
            long end_count = salesPeriodJpaRepository.count();

            Assert.assertEquals(start_count-1, end_count);
        } else {
            Assert.assertEquals(start_count, 0);
        }
    }

    @Test
    public void deleteAllTest() {
        Assert.assertTrue(salesPeriodJpaRepository.count() > 0);

        salesPeriodJpaRepository.deleteAll();
        Assert.assertEquals(salesPeriodJpaRepository.count(), 0);
    }

    @Test
    public void deleteByObjectTest() {
        Optional<Product> optionalProduct = productJpaRepository.findById(1);
        Assert.assertTrue(optionalProduct.isPresent());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        SalesPeriod salesPeriod = null;
        try {
            salesPeriod = new SalesPeriod(1, 100, simpleDateFormat.parse("2020-05-01"), simpleDateFormat.parse("2020-05-04"), optionalProduct.get());
        } catch (ParseException e) {
            Assert.assertFalse(true);
        }
        salesPeriodJpaRepository.delete(salesPeriod);

        Optional<SalesPeriod> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(1L);
        Assert.assertFalse(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void findByPriceTest() {
        List<SalesPeriod> salesPeriod = salesPeriodJpaRepository.findByPrice(100L);
        Assert.assertNotNull(salesPeriod);
        Assert.assertEquals(salesPeriod.get(0).getPrice(), 100L);
    }

    @Test
    public void findAllByNameTest() {
        List<SalesPeriod> salesPeriod = salesPeriodJpaRepository.findByPrice(100L);
        Assert.assertNotNull(salesPeriod);
        Assert.assertEquals(2, salesPeriod.size());
    }

    @Test
    public void findByNameNegativeTest() {
        List<SalesPeriod> salesPeriod = salesPeriodJpaRepository.findByPrice(10);
        Assert.assertNotNull(salesPeriod);
        Assert.assertEquals(salesPeriod.size(), 0);
    }

    @Test
    public void findByIdTest() {
        Optional<SalesPeriod> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(1L);
        Assert.assertTrue(optionalSalesPeriodJpaDemo.isPresent());
        Assert.assertEquals(optionalSalesPeriodJpaDemo.get().getId(), 1);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<SalesPeriod> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(-1L);
        Assert.assertFalse(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void findByDateToIsNullTest() {
        List<SalesPeriod> salesPeriod = salesPeriodJpaRepository.findByDateToIsNull();
        Assert.assertEquals(1, salesPeriod.size());
    }

    @Test
    public void existsByPriceTest() {
        Assert.assertTrue(salesPeriodJpaRepository.existsByPrice(100));
    }
}
