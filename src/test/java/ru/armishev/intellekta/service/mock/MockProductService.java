package ru.armishev.intellekta.service.mock;

import org.springframework.stereotype.Service;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.service.ProductService;

import java.util.ArrayList;
import java.util.List;

@Service
public class MockProductService implements ProductService {
    @Override
    public List<Product> findAll() {
        return new ArrayList<>();
    }

    @Override
    public Product findById(Object id) {
        return new Product(Integer.parseInt(String.valueOf(id)), "testProduct");
    }

    @Override
    public Product create(Product product) {
        return product;
    }

    @Override
    public Product update(Product product) {
        return product;
    }

    @Override
    public void delete(Object id) {

    }
}
