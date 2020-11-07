package ru.armishev.intellekta.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.armishev.intellekta.entity.SalesPeriodJdbcDemo;
import ru.armishev.intellekta.entity.Product;

import java.util.*;

@Repository
public class SalesPeriodJdbcRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public SalesPeriodJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public int count() {
        Integer result = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM public.sales_period", Integer.class);
        return (result == null) ? 0 : result;
    }

    public int getMaxId() {
        Integer result = jdbcTemplate.queryForObject("SELECT MAX(id) FROM public.sales_period", Integer.class);
        return (result == null) ? 0 : result;
    }

    public List<SalesPeriodJdbcDemo> getSalesPeriods() {
        return jdbcTemplate.query("SELECT * FROM public.sales_period", (rs, rNUm)->{
            return new SalesPeriodJdbcDemo(rs.getLong("id"),
                    rs.getLong("price"),
                    rs.getDate("date_from"),
                    rs.getDate("date_to"),
                    rs.getInt("product"));
        });
    }

    public List<SalesPeriodJdbcDemo> getSalesPeriodsPriceIsHigher(long price) {
        return jdbcTemplate.query(String.format("SELECT * FROM public.sales_period WHERE price >= %s", price), (rs, rNUm)->{
            return new SalesPeriodJdbcDemo(rs.getLong("id"),
                    rs.getLong("price"),
                    rs.getDate("date_from"),
                    rs.getDate("date_to"),
                    rs.getInt("product"));
        });
    }

    public List<Product> getProductsWithActivePeriod() {
        return jdbcTemplate.query("SELECT p.id product_id, p.name product_name FROM public.product p " +
                "INNER JOIN public.sales_period sp ON p.id = sp.product " +
                "WHERE sp.date_to IS NULL", (rs, rNUm)->{
                    return new Product(rs.getInt("product_id"),
                    rs.getString("product_name"));
        });
    }

    public void addItem(SalesPeriodJdbcDemo salesPeriodJdbcDemo) {
        jdbcTemplate.update("INSERT INTO public.sales_period (id, price, date_from, date_to, product) VALUES (?, ?, ?, ?, ?)",
                salesPeriodJdbcDemo.getId(),
                salesPeriodJdbcDemo.getPrice(),
                salesPeriodJdbcDemo.getDateFrom(),
                salesPeriodJdbcDemo.getDateTo(),
                salesPeriodJdbcDemo.getProduct());
    }
}
