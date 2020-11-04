package ru.armishev.intellekta.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.armishev.intellekta.jpa.Product;
import ru.armishev.intellekta.jpa.ProductRepository;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping(value="product/")
public class ProductController {
    @Autowired
    ProductRepository productRepository;

    @GetMapping("add/")
    public Product addProduct(HttpServletRequest request) {
        String requestParameter = request.getParameter("name");
        if (requestParameter == null) {
            throw new IllegalArgumentException("Нужно передать имя");
        }

        Product newProduct = new Product();
        newProduct.setName(requestParameter);

        return productRepository.save(newProduct);
    }

    @GetMapping("list/")
    public List<Product> getList() {
        return productRepository.findAll();
    }
}
