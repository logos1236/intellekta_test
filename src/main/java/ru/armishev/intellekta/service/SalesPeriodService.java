package ru.armishev.intellekta.service;

import org.springframework.web.bind.annotation.RequestParam;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;

import java.util.List;

public interface SalesPeriodService {
    List<SalesPeriod> findAll();
    SalesPeriod findById(Object id);
    SalesPeriod create(SalesPeriod salesPeriod);
    SalesPeriod update(SalesPeriod salesPeriod);
    void delete(Object id);
    List<SalesPeriod> init();
    long count();
    int getMaxPriceByProductId(int product_id);
    boolean existsByPrice(int price);
    List<SalesPeriod> findByDateToIsNull();
    List<SalesPeriod> findByProductName(String name);
}
