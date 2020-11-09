package ru.armishev.intellekta.service;

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

import java.util.*;

@Service
public class ProductService {
    private final ProductJpaRepository productJpaRepository;

    private final SalesPeriodJpaRepository salesPeriodJpaRepository;

    @Autowired
    public ProductService(ProductJpaRepository productJpaRepository, SalesPeriodJpaRepository salesPeriodJpaRepository) {
        this.productJpaRepository = productJpaRepository;
        this.salesPeriodJpaRepository = salesPeriodJpaRepository;
    }

    public List<Product> findAll() {
        return productJpaRepository.findAll();
    }

    public Product findById(Object id) {
        Product product;

        if (id == null) {
            throw new EntityIllegalArgumentException("Идентификатор объекта не может быть null");
        }

        Integer parseId;
        try {
            parseId = Integer.valueOf((String) id);
        } catch (NumberFormatException ex) {
            throw new EntityIllegalArgumentException(String.format("Не удалось преобразовать идентификатор " +
                    "к нужному типу, текст ошибки: %s", ex));
        }

        product = productJpaRepository.getOne(parseId);

        if (product == null) {
            throw new EntityNotFoundException(Product.TYPE_NAME, parseId);
        }

        return product;
    }

    public Product create(Product product) {
        if (product == null) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть null");
        }

        if (product.getId() == 0) {
            throw new EntityIllegalArgumentException("Создаваемый объект не может быть 0");
        }

        Product existedProduct = productJpaRepository.getOne(product.getId());
        if (existedProduct != null) {
            throw new EntityAlreadyExistException(Product.TYPE_NAME, product.getId());
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
