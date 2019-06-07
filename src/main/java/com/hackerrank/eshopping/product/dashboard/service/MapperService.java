package com.hackerrank.eshopping.product.dashboard.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class MapperService {

    private ObjectMapper objectMapper() {
        return new ObjectMapper();
    }


    public ProductDTO mapJsonToDTO(String json) throws RuntimeException, IOException {
        try {
            return objectMapper().readValue(json, ProductDTO.class);
        } catch (RuntimeException e) {
            throw new RuntimeException("Error try Map json to Object");
        }
    }

    public String mapObjectListToJson(List<Product> productList) throws JsonProcessingException {
        return objectMapper().writeValueAsString(productList);
    }

    public String mapObjectToJson(Product product) throws JsonProcessingException {
        return objectMapper().writeValueAsString(product);
    }

}
