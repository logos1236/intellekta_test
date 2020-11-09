package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping(value="sales/jpa/")
public class SalesControllerJpa {
    @Autowired
    private SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    ProductJpaRepository productRepository;

    @GetMapping("")
    public List<SalesPeriod> getSalesPeriodJpa() {
        return salesPeriodJpaRepository.findAll();
    }

    @PostMapping("")
    public SalesPeriod addSalesPeriodJpa(@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodJpaRepository.save(salesPeriod);
    }

    @GetMapping("init/")
    public List<SalesPeriod> initSalesPeriods() {
        List<Product> products = productRepository.findAll();
        int id = salesPeriodJpaRepository.getMaxId();
        List<SalesPeriod> result = new ArrayList<>();

        for(int i=0; i<10; i++) {
            int minusDateFrom = ThreadLocalRandom.current().nextInt(100, 500 + 1);
            int minusDateTo = ThreadLocalRandom.current().nextInt(100, minusDateFrom);

            int price = ThreadLocalRandom.current().nextInt(40, 500 + 1);
            Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(minusDateFrom)));
            Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(minusDateTo)));
            Product product = (products.size() > 0) ? products.get(ThreadLocalRandom.current().nextInt(0, products.size())) : null;

            result.add(new SalesPeriod(++id, price, dateFrom, dateTo, product));
        }

        if (!result.isEmpty()) {
            salesPeriodJpaRepository.saveAll(result);
        }

        return salesPeriodJpaRepository.findAll();
    }

    @GetMapping("count/")
    public long getSalesCount() {
        return salesPeriodJpaRepository.count();
    }

    @GetMapping("max/price/")
    public Integer getMaxPriceByProductId() {
        return salesPeriodJpaRepository.getMaxPriceFromProductId(1);
    }

    @GetMapping("exists/price/")
    public boolean existsByPrice(@RequestParam(name = "price") int price) {
        return salesPeriodJpaRepository.existsByPrice(price);
    }

    @GetMapping("active/")
    public List<SalesPeriod> findByDateToIsNull() {
        return salesPeriodJpaRepository.findByDateToIsNull();
    }

    @GetMapping("by-product-name/")
    public List<SalesPeriod> findByProductName(@RequestParam(name = "name") String name) {
        return salesPeriodJpaRepository.findByProductName(name);
    }
}
