package ru.armishev.intellekta.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;

import java.util.*;

@Repository
public interface SalesPeriodJpaRepository extends JpaRepository<SalesPeriod, Long> {
    @Query(value = "SELECT MAX(price) from sales_period where product = :productId", nativeQuery = true)
    Integer getMaxPriceFromProductId(@Param("productId") long productId);

    boolean existsByPrice(long price);

    List<SalesPeriod> findByDateToIsNull();

    List<SalesPeriod> findByProductName(String productName);

    @Query(value = "SELECT MAX(id) from sales_period", nativeQuery = true)
    long getMaxId();

    List<SalesPeriod> findByPrice(long price);

    List<SalesPeriod> findByProduct(Product product);

    List<SalesPeriod> findByDateToIsNullAndProductId(int productId);
}
