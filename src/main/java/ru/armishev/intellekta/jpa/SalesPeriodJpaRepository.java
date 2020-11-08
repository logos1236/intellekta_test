package ru.armishev.intellekta.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.armishev.intellekta.entity.SalesPeriodJpaDemo;

import java.util.*;

@Repository
public interface SalesPeriodJpaRepository extends JpaRepository<SalesPeriodJpaDemo, Long> {
    @Query(value = "SELECT MAX(price) from sales_period where product = :productId", nativeQuery = true)
    Integer getMaxPriceFromProductId(@Param("productId") long productId);

    boolean existsByPrice(long price);

    List<SalesPeriodJpaDemo> findByDateToIsNull();

    List<SalesPeriodJpaDemo> findByProductName(String productName);

    @Query(value = "SELECT MAX(id) from sales_period", nativeQuery = true)
    Integer getMaxId();
}
