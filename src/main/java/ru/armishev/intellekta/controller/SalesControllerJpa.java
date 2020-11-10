package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.service.SalesPeriodService;

import java.util.List;

@RestController
@RequestMapping(value="sales/jpa/")
public class SalesControllerJpa {
    @Autowired
    private SalesPeriodService salesPeriodService;

    @GetMapping
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public List<SalesPeriod> getSalesPeriodJpa() {
        return salesPeriodService.findAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasPermission('salesPeriod','readById')")
    public SalesPeriod getSalesPeriodJpaId(@PathVariable int id) {
        return salesPeriodService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermission('salesPeriod','write')")
    public SalesPeriod create(@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodService.create(salesPeriod);
    }

    @GetMapping("init/")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermission('salesPeriod','write')")
    public List<SalesPeriod> init() {
        return salesPeriodService.init();
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('salesPeriod','write')")
    public SalesPeriod update(@RequestBody SalesPeriod salesPeriod) {
        return salesPeriodService.update(salesPeriod);
    }

    @GetMapping("count/")
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public long getSalesCount() {
        return salesPeriodService.count();
    }

    @GetMapping("max/price/")
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public Integer getMaxPriceByProductId(@RequestParam(name = "product_id") int product_id) {
        return salesPeriodService.getMaxPriceByProductId(product_id);
    }

    @GetMapping("exists/price/")
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public boolean existsByPrice(@RequestParam(name = "price") int price) {
        return salesPeriodService.existsByPrice(price);
    }

    @GetMapping("active/")
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public List<SalesPeriod> findByDateToIsNull() {
        return salesPeriodService.findByDateToIsNull();
    }

    @GetMapping("by-product-name/")
    @PreAuthorize("hasPermission('salesPeriod','read')")
    public List<SalesPeriod> findByProductName(@RequestParam(name = "name") String name) {
        return salesPeriodService.findByProductName(name);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission('salesPeriod','delete')")
    public void delete(@PathVariable String id) {
        salesPeriodService.delete(id);
    }
}
