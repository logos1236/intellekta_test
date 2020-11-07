package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.SalesPeriodJpaDemo;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;

import java.util.List;

@RestController
@RequestMapping(value="sales/jpa/")
public class SalesControllerJpa {
    @Autowired
    private SalesPeriodJpaRepository salesPeriodJpaRepository;

    @GetMapping("jpa/")
    public List<SalesPeriodJpaDemo> getSalesPeriodJpa() { return salesPeriodJpaRepository.findAll(); }

    @PostMapping("jpa/")
    public SalesPeriodJpaDemo addSalesPeriodJpa(@RequestBody SalesPeriodJpaDemo salesPeriodJpaDemo) {
        return salesPeriodJpaRepository.save(salesPeriodJpaDemo);
    }

    @GetMapping("jpa/max/price/")
    public Integer getMaxPriceByProductId() { return salesPeriodJpaRepository.getMaxPriceFromProductId(1); }

    @GetMapping("jpa/exists/price/")
    public boolean existsByPrice(@RequestParam(name = "price") int price) { return salesPeriodJpaRepository.existsByPrice(price); }

    @GetMapping("jpa/active/")
    public List<SalesPeriodJpaDemo> findByDateToIsNull() { return salesPeriodJpaRepository.findByDateToIsNull(); }

    @GetMapping("jpa/by-product-name/")
    public List<SalesPeriodJpaDemo> findByProductName(@RequestParam(name = "name") String name) { return salesPeriodJpaRepository.findByProductName(name); }
}
