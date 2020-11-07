package ru.armishev.intellekta.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.armishev.intellekta.entity.Product;

@Repository
public interface ProductJpaRepository extends JpaRepository<Product, Integer> {

}
