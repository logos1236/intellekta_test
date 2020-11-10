package ru.armishev.intellekta.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.armishev.intellekta.entity.Product;
import ru.armishev.intellekta.entity.SalesPeriod;
import ru.armishev.intellekta.service.mock.MockSalesProductService;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {SalesControllerJpa.class, MockSalesProductService.class})
public class SalesControllerJpaTest {
    @Autowired
    private SalesControllerJpa salesControllerJpa;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/sales/jpa/";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(salesControllerJpa).build();
    }

    @Test
    public void findAllTest() throws Exception {
        mockMvc.perform(get(URL))
                .andExpect(status().isOk());
    }

    @Test
    public void findByIdTest() throws Exception {
        int id_product = 1;

        mockMvc.perform(get(URL+id_product+"/"))
                .andExpect(status().isOk());
    }

    @Test
    public void createTest() throws Exception {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(4)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(2)));
        Product product = new Product(1,"test");

        SalesPeriod salesPeriod = new  SalesPeriod(1, 100, dateFrom, dateTo, product);

        String requestJson = mapper.writeValueAsString(salesPeriod);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void initTest() throws Exception {
        mockMvc.perform(get(URL+"init/"))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception {
        Date dateFrom = Date.from(Instant.now().minus(Duration.ofDays(4)));
        Date dateTo = Date.from(Instant.now().minus(Duration.ofDays(2)));
        Product product = new Product(1,"test");

        SalesPeriod salesPeriod = new  SalesPeriod(1, 100, dateFrom, dateTo, product);

        String requestJson = mapper.writeValueAsString(salesPeriod);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        int id = 1;

        mockMvc.perform(delete(URL+id+"/"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void countTest() throws Exception {
        mockMvc.perform(get(URL+"count/"))
                .andExpect(status().isOk());
    }

    @Test
    public void maxPriceTest() throws Exception {
        int productId = 1;

        mockMvc.perform(get(URL+"max/price/").param("product_id", String.valueOf(productId)))
                .andExpect(status().isOk());
    }

    @Test
    public void existsByPriceTest() throws Exception {
        int price = 400;

        mockMvc.perform(get(URL+"exists/price/").param("price", String.valueOf(price)))
                .andExpect(status().isOk());
    }

    @Test
    public void activeTest() throws Exception {
        mockMvc.perform(get(URL+"active/"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByProductNameTest() throws Exception {
        String name = "test";

        mockMvc.perform(get(URL+"by-product-name/").param("name", name))
                .andExpect(status().isOk());
    }
}
