package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;
import ru.armishev.intellekta.service.ProductService;
import ru.armishev.intellekta.service.SalesPeriodService;

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
    private SalesPeriodService salesPeriodService;

    @GetMapping
    public List<SalesPeriod> getSalesPeriodJpa() {
        return salesPeriodService.findAll();
    }

    @GetMapping("{id}")
    public SalesPeriod getSalesPeriodJpaId(@PathVariable int id) {
        return salesPeriodService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SalesPeriod create(@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodService.create(salesPeriod);
    }

    @GetMapping("init/")
    @ResponseStatus(HttpStatus.CREATED)
    public List<SalesPeriod> init() {
        return salesPeriodService.init();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public SalesPeriod update(@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodService.update(salesPeriod);
    }

    @GetMapping("count/")
    public long getSalesCount() {
        return salesPeriodService.count();
    }

    @GetMapping("max/price/")
    public Integer getMaxPriceByProductId(@RequestParam(name = "product_id") int product_id) {
        return salesPeriodService.getMaxPriceByProductId(product_id);
    }

    @GetMapping("exists/price/")
    public boolean existsByPrice(@RequestParam(name = "price") int price) {
        return salesPeriodService.existsByPrice(price);
    }

    @GetMapping("active/")
    public List<SalesPeriod> findByDateToIsNull() {
        return salesPeriodService.findByDateToIsNull();
    }

    @GetMapping("by-product-name/")
    public List<SalesPeriod> findByProductName(@RequestParam(name = "name") String name) {
        return salesPeriodService.findByProductName(name);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        salesPeriodService.delete(id);
    }
}
