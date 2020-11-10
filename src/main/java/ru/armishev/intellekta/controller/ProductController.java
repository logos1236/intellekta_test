package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.service.ProductService;

import java.util.List;

@RestController
@RequestMapping(value="api/v1/product/")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping
    @PreAuthorize("hasPermission('product','read')")
    public List<Product> getList() {
        return productService.findAll();
    }

    @GetMapping("{id}")
    @PreAuthorize("hasPermission('product','readById')")
    public Product findById(@PathVariable String id) {
        return productService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasPermission('product','write')")
    public Product create(@RequestBody Product product) {
        return productService.create(product);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasPermission('product','write')")
    public Product update(@RequestBody Product product) {
        return productService.update(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasPermission('product','delete')")
    public void delete(@PathVariable String id) {
        productService.delete(id);
    }
}
