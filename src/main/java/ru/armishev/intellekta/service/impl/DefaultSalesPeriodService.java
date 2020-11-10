package ru.armishev.intellekta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.exceptions.*;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;
import ru.armishev.intellekta.service.ProductService;
import ru.armishev.intellekta.service.SalesPeriodService;

import java.beans.Transient;
import java.time.Duration;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class DefaultSalesPeriodService implements SalesPeriodService {
    @Autowired
    private SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    private ProductJpaRepository productJpaRepository;

    @Override
    public List<SalesPeriod> findAll() {
        return salesPeriodJpaRepository.findAll();
    }

    @Override
    public SalesPeriod findById(Object id) {
        SalesPeriod salesPeriod = null;

        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }

        long parseId;
        try {
            parseId = Long.valueOf(String.valueOf(id));
        } catch (NumberFormatException ex) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }

        Optional<SalesPeriod> optionalSalesPeriod = salesPeriodJpaRepository.findById(parseId);
        if (!optionalSalesPeriod.isPresent()) {
            throw new EntityNotFoundException(Product.TYPE_NAME, parseId);
        }

        salesPeriod = optionalSalesPeriod.get();

        return salesPeriod;
    }

    @Override
    public SalesPeriod create(SalesPeriod salesPeriod) {
        if (salesPeriod == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (salesPeriod.getId() == 0) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть 0");
        }

        if (salesPeriod.getProduct() == null) {
            throw new EntityIllegalArgumentException("Продукт не может быть null");
        }

        if (salesPeriod.getProduct().getId() == 0) {
            throw new EntityIllegalArgumentException("Идентификатор продукта не может быть 0");
        }

        if (salesPeriod.getDateFrom() == null) {
            throw new EntityIllegalArgumentException("Дата начала периода не может быть null");
        }

        Optional<SalesPeriod> existedSalesPeriod = salesPeriodJpaRepository.findById(salesPeriod.getId());
        if (existedSalesPeriod.isPresent()) {
            throw new EntityAlreadyExistException(SalesPeriod.TYPE_NAME, existedSalesPeriod.get().getId());
        }

        return salesPeriodJpaRepository.save(salesPeriod);
    }

    @Override
    public SalesPeriod update(SalesPeriod salesPeriod) {
        if (salesPeriod == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (salesPeriod.getId() == 0) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть 0");
        }

        if (salesPeriod.getProduct() == null) {
            throw new EntityIllegalArgumentException("Продукт не может быть null");
        }

        if (salesPeriod.getProduct().getId() == 0) {
            throw new EntityIllegalArgumentException("Идентификатор продукта не может быть 0");
        }

        if (salesPeriod.getDateFrom() == null) {
            throw new EntityIllegalArgumentException("Дата начала периода не может быть null");
        }

        Optional<SalesPeriod> existedSalesPeriod = salesPeriodJpaRepository.findById(salesPeriod.getId());
        if (!existedSalesPeriod.isPresent()) {
            throw new EntityAlreadyExistException(SalesPeriod.TYPE_NAME, existedSalesPeriod.get().getId());
        }

        return salesPeriodJpaRepository.save(salesPeriod);
    }

    @Override
    public void delete(Object id) {
        SalesPeriod salesPeriod = findById(id);

        List<SalesPeriod> lastSalesPeriods = salesPeriodJpaRepository.findByDateToIsNullAndProductId(salesPeriod.getProduct().getId());
        if (!lastSalesPeriods.isEmpty()) {
            throw new EntityConflictException(String.format("В системе имеются открытые торговые периоды для продукта с id %s", salesPeriod.getProduct().getId()));
        }

        salesPeriodJpaRepository.delete(salesPeriod);
    }

    @Override
    public List<SalesPeriod> init() {
        List<Product> products = productJpaRepository.findAll();
        List<SalesPeriod> result = new ArrayList<>();
        long nextId = 1;

        for(int i=0; i<10; i++) {
            int minusDateFrom = ThreadLocalRandom.current().nextInt(100, 500 + 1);
            int minusDateTo = ThreadLocalRandom.current().nextInt(100, minusDateFrom);

            int price = ThreadLocalRandom.current().nextInt(40, 500 + 1);
            Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(minusDateFrom)));
            Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(minusDateTo)));
            Product product = (products.size() > 0) ? products.get(ThreadLocalRandom.current().nextInt(0, products.size())) : null;

            result.add(new SalesPeriod(nextId+i, price, dateFrom, dateTo, product));
        }

        if (!result.isEmpty()) {
            salesPeriodJpaRepository.saveAll(result);
        }

        return salesPeriodJpaRepository.findAll();
    }

    @Override
    public long count() {
        return salesPeriodJpaRepository.count();
    }

    @Override
    public int getMaxPriceByProductId(int product_id) {
        return salesPeriodJpaRepository.getMaxPriceFromProductId(product_id);
    }

    @Override
    public boolean existsByPrice(int price) {
        return salesPeriodJpaRepository.existsByPrice(price);
    }

    @Override
    public List<SalesPeriod> findByDateToIsNull() {
        return salesPeriodJpaRepository.findByDateToIsNull();
    }

    @Override
    public List<SalesPeriod> findByProductName(String name) {
        return salesPeriodJpaRepository.findByProductName(name);
    }
}
