package com.hackerrank.eshopping.product.dashboard.utils.Json;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class JsonUtil {

    public JsonUtil() {
    }


    /**
     * Json Validator
     *
     * @param jsonInString
     * @throws IOException
     */
    public static void jsonValidator(String jsonInString) throws IOException {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.readTree(jsonInString);
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }


}
