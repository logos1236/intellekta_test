package ru.armishev.intellekta.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.exceptions.EntityAlreadyExistException;
import ru.armishev.intellekta.exceptions.EntityHasDetailException;
import ru.armishev.intellekta.exceptions.EntityIllegalArgumentException;
import ru.armishev.intellekta.exceptions.EntityNotFoundException;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.jpa.SalesPeriodJpaRepository;
import ru.armishev.intellekta.service.ProductService;

import java.util.*;

@Service
public class DefaultProductService implements ProductService {
    private final ProductJpaRepository productJpaRepository;

    private final SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    public DefaultProductService(ProductJpaRepository productJpaRepository, SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.productJpaRepository = productJpaRepository;
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    public Product findById(Object id) {
        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }

        Integer parseId;
        try {
            parseId = Integer.parseInt(String.valueOf(id));
        } catch (NumberFormatException ex) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }

        Optional<Product> optionalProduct = productJpaRepository.findById(parseId);
        if (!productJpaRepository.findById(parseId).isPresent()) {
            throw new EntityNotFoundException(Product.TYPE_NAME, parseId);
        }


        return optionalProduct.get();
    }

    public Product create(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (product.getId() == 0) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть 0");
        }

        Optional<Product> optionalExistedProduct = productJpaRepository.findById(product.getId());
        if (optionalExistedProduct.isPresent()) {
            throw new EntityAlreadyExistException(Product.TYPE_NAME, optionalExistedProduct.get().getId());
        }

        return productJpaRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (product.getId() == 0) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть 0");
        }

        Optional<Product> optionalExistedProduct = productJpaRepository.findById(product.getId());
        if (!optionalExistedProduct.isPresent()) {
            throw new EntityNotFoundException(Product.TYPE_NAME, optionalExistedProduct.get().getId());
        }

        return productJpaRepository.save(product);
    }

    public void delete(Object id) {
        Product product = findById(id);
        List<SalesPeriod> salesPeriods = salesPeriodJpaRepository.findByProduct(product);

        if (!salesPeriods.isEmpty()) {
            throw new EntityHasDetailException(SalesPeriod.TYPE_NAME, product.getId());
        }

        productJpaRepository.delete(product);
    }
}
