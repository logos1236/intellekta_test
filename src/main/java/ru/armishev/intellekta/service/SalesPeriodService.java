package ru.armishev.intellekta.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.exceptions.*;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;

import java.util.*;

@Service
public class SalesPeriodService {

    private final SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    public SalesPeriodService(SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    public List<SalesPeriod> findAll() {
        return salesPeriodJpaRepository.findAll();
    }

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

    public void delete(Object id) {
        SalesPeriod salesPeriod = findById(id);

        List<SalesPeriod> lastSalesPeriods = salesPeriodJpaRepository.findByDateToIsNullAndProductId(salesPeriod.getProduct().getId());
        if (!lastSalesPeriods.isEmpty()) {
            throw new EntityConflictException(String.format("В системе имеются открытые торговые периоды для продукта с id %s", salesPeriod.getProduct().getId()));
        }

        salesPeriodJpaRepository.delete(salesPeriod);
    }
}
