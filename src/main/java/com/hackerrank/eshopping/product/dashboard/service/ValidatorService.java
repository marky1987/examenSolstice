package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.utils.Json.JsonUtil;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ValidatorService {
    public void validateJson(String json) throws IOException {
        JsonUtil.jsonValidator(json);
    }
}
