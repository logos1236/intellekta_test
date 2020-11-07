package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.SalesPeriodJdbcDemo;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.jdbc.SalesPeriodJdbcRepository;
import ru.armishev.intellekta.entity.Product;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(value="sales/")
public class SalesController {
    @Autowired
    private SalesPeriodJdbcRepository salesPeriodJdbcRepository;

    @Autowired
    ProductJpaRepository productRepository;

    @GetMapping("")
    public List<SalesPeriodJdbcDemo> getSalesPeriods() {
        return salesPeriodJdbcRepository.getSalesPeriods();
    }

    @GetMapping("init/")
    public void initSalesPeriods() {
        List<Product> products = productRepository.findAll();
        int id = salesPeriodJdbcRepository.getMaxId();

        for(int i=0; i<10; i++) {
            int minusDateFrom = ThreadLocalRandom.current().nextInt(100, 500 + 1);
            int minusDateTo = ThreadLocalRandom.current().nextInt(100, minusDateFrom);
            int productRandomPosition = ThreadLocalRandom.current().nextInt(0, products.size());

            int price = ThreadLocalRandom.current().nextInt(40, 500 + 1);
            Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(minusDateFrom)));
            Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(minusDateTo)));
            Integer productId = (productRandomPosition > 0) ? products.get(productRandomPosition).getId() : null;

            salesPeriodJdbcRepository.addItem(new SalesPeriodJdbcDemo(++id, price, dateFrom, dateTo, productId));
        }
    }

    @GetMapping("count/")
    public Integer getSalesCount() {
        return salesPeriodJdbcRepository.count();
    }

    @GetMapping("by-higher-price/")
    public List<SalesPeriodJdbcDemo> getSalesPeriodsByHigherPrice() {
        return salesPeriodJdbcRepository.getSalesPeriodsPriceIsHigher(90);
    }
}
