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
import ru.armishev.intellekta.service.mock.MockProductService;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductController.class, MockProductService.class})
public class ProductControllerTest {
    @Autowired
    private ProductController productController;

    private MockMvc mockMvc;

    private final static String URL = "http://localhost:8080/api/v1/product/";

    ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = standaloneSetup(productController).build();
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
        Product product = new Product(3, "testProduct");
        String requestJson = mapper.writeValueAsString(product);

        mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void updateTest() throws Exception {
        Product product = new Product(1, "testProduct");
        String requestJson = mapper.writeValueAsString(product);

        mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk());
    }

    @Test
    public void deleteTest() throws Exception {
        int id_product = 1;

        mockMvc.perform(delete(URL+id_product+"/"))
                .andExpect(status().isNoContent());
    }
}
