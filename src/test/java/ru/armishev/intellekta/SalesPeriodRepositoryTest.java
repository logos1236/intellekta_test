package ru.armishev.intellekta;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriodJpaDemo;
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

        SalesPeriodJpaDemo salesPeriodJpaDemo = new SalesPeriodJpaDemo(7, 300, dateFrom, dateTo, optionalProduct.get());
        salesPeriodJpaRepository.save(salesPeriodJpaDemo);

        Optional<SalesPeriodJpaDemo> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(7L);
        Assert.assertTrue(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void createTestRandomIdTest() {
        try {
            Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(10)));
            Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(5)));
            Optional<Product> optionalProduct = productJpaRepository.findById(1);
            Assert.assertTrue(optionalProduct.isPresent());

            SalesPeriodJpaDemo salesPeriodJpaDemo = new SalesPeriodJpaDemo(71111, 300, dateFrom, dateTo, optionalProduct.get());
            salesPeriodJpaRepository.save(salesPeriodJpaDemo);

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

        SalesPeriodJpaDemo salesPeriodJpaDemo = null;
        try {
            salesPeriodJpaDemo = new SalesPeriodJpaDemo(1, 100, simpleDateFormat.parse("2020-05-01"), simpleDateFormat.parse("2020-05-04"), optionalProduct.get());
        } catch (ParseException e) {
            Assert.assertFalse(true);
        }
        salesPeriodJpaRepository.delete(salesPeriodJpaDemo);

        Optional<SalesPeriodJpaDemo> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(1L);
        Assert.assertFalse(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void findByPriceTest() {
        List<SalesPeriodJpaDemo> salesPeriodJpaDemo = salesPeriodJpaRepository.findByPrice(100L);
        Assert.assertNotNull(salesPeriodJpaDemo);
        Assert.assertEquals(salesPeriodJpaDemo.get(0).getPrice(), 100L);
    }

    @Test
    public void findAllByNameTest() {
        List<SalesPeriodJpaDemo> salesPeriodJpaDemo = salesPeriodJpaRepository.findByPrice(100L);
        Assert.assertNotNull(salesPeriodJpaDemo);
        Assert.assertEquals(2, salesPeriodJpaDemo.size());
    }

    @Test
    public void findByNameNegativeTest() {
        List<SalesPeriodJpaDemo> salesPeriodJpaDemo = salesPeriodJpaRepository.findByPrice(10);
        Assert.assertNotNull(salesPeriodJpaDemo);
        Assert.assertEquals(salesPeriodJpaDemo.size(), 0);
    }

    @Test
    public void findByIdTest() {
        Optional<SalesPeriodJpaDemo> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(1L);
        Assert.assertTrue(optionalSalesPeriodJpaDemo.isPresent());
        Assert.assertEquals(optionalSalesPeriodJpaDemo.get().getId(), 1);
    }

    @Test
    public void findByIdNegativeTest() {
        Optional<SalesPeriodJpaDemo> optionalSalesPeriodJpaDemo = salesPeriodJpaRepository.findById(-1L);
        Assert.assertFalse(optionalSalesPeriodJpaDemo.isPresent());
    }

    @Test
    public void findByDateToIsNullTest() {
        List<SalesPeriodJpaDemo> salesPeriodJpaDemo = salesPeriodJpaRepository.findByDateToIsNull();
        Assert.assertEquals(1, salesPeriodJpaDemo.size());
    }

    @Test
    public void existsByPriceTest() {
        Assert.assertTrue(salesPeriodJpaRepository.existsByPrice(100));
    }
}
