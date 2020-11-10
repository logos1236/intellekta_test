package ru.armishev.intellekta.service.mock;

import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.service.SalesPeriodService;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MockSalesProductService implements SalesPeriodService {
    @Override
    public List<SalesPeriod> findAll() {
        return new ArrayList<>();
    }

    @Override
    public SalesPeriod findById(Object id) {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(4)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(2)));
        Product product = new Product(1,"test");

        return new SalesPeriod(1, 100, dateFrom, dateTo, product);
    }

    @Override
    public SalesPeriod create(SalesPeriod salesPeriod) {
        return salesPeriod;
    }

    @Override
    public SalesPeriod update(SalesPeriod salesPeriod) {
        return salesPeriod;
    }

    @Override
    public void delete(Object id) {

    }

    @Override
    public List<SalesPeriod> init() {
        return new ArrayList<>();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public int getMaxPriceByProductId(int product_id) {
        return 0;
    }

    @Override
    public boolean existsByPrice(int price) {
        return true;
    }

    @Override
    public List<SalesPeriod> findByDateToIsNull() {
        return new ArrayList<>();
    }

    @Override
    public List<SalesPeriod> findByProductName(String name) {
        return new ArrayList<>();
    }
}
