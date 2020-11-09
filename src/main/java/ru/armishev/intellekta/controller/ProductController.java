package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.jdbc.SalesPeriodJdbcRepository;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.jpa.ProductJpaRepository;
import ru.armishev.intellekta.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="api/v1/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    public List<Product> getList() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    public Product findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }
}
