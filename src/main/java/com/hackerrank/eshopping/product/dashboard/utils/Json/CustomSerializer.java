package com.hackerrank.eshopping.product.dashboard.utils.Json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.hackerrank.eshopping.product.dashboard.model.Product;

import java.io.IOException;

public class CustomSerializer extends JsonSerializer<Product> {

    public void serialize(Product value, JsonGenerator jgen,
                          SerializerProvider provider) throws IOException,
            JsonProcessingException {
        if (value != null) {
            jgen.writeStartObject();

            jgen.writeStringField("retail_price", String.valueOf(value.getRetailPrice()));
            jgen.writeStringField("discounted_price", String.valueOf(value.getDiscountedPrice()));

            jgen.writeEndObject();
        }
    }
}