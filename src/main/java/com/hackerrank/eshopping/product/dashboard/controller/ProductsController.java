package com.hackerrank.eshopping.product.dashboard.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.exception.NotFoundDataException;
import com.hackerrank.eshopping.product.dashboard.exception.ProductException;
import com.hackerrank.eshopping.product.dashboard.service.MapperService;
import com.hackerrank.eshopping.product.dashboard.service.ProductService;
import com.hackerrank.eshopping.product.dashboard.service.ValidatorService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping(value = "/products")
@Api(tags = "products")
public class ProductsController {

    private ValidatorService validatorService;
    private MapperService mapperService;
    private ProductService productService;

    @Autowired
    public ProductsController(ValidatorService validatorService, MapperService mapperService, ProductService productService) {
        this.validatorService = validatorService;
        this.mapperService = mapperService;
        this.productService = productService;
    }

    /**
     * Add product
     *
     * @return
     */
    @PostMapping(consumes = "application/json")
    @ApiOperation(value = "Create Product", notes = "Service to create/add products")
    @ApiResponses(value = {@ApiResponse(code = 201, message = "Product added"),
            @ApiResponse(code = 400, message = "Product cannot added")})
    public ResponseEntity<?> addProduct(@RequestBody String json) throws IOException, ProductException {
        validatorService.validateJson(json);
        productService.createProduct(mapperService.mapJsonToDTO(json));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping(produces = "application/json")
    @ApiOperation(value = "Get List of Products", notes = "Service to get list of products")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List of products"),
            @ApiResponse(code = 400, message = "Cannot get the list")})
    public ResponseEntity<?> listProducts() throws IOException {
        mapperService.mapObjectListToJson(productService.getAllProducts());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Update product by Id
     *
     * @param productId
     * @return
     */
    @PutMapping(value = "/{product_id}")
    @ApiOperation(value = "Update Product", notes = "Service to update product by ProductId")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product updated"),
            @ApiResponse(code = 400, message = "Product not found to be updated")})
    public ResponseEntity<?> updateProducts(
            @PathVariable("product_id") Long productId, @RequestBody String json) throws IOException {
        ProductDTO product = mapperService.mapJsonToDTO(json);
        productService.updateProduct(productId, product);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * Return specific product filtered by Id
     *
     * @param productId
     * @return
     */
    @GetMapping(value = "/{product_id}")
    @ApiOperation(value = "Fetch Product", notes = "Service to fetch product by ProductId")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "Product founded"),
            @ApiResponse(code = 400, message = "Product cannot be found")})
    @ResponseBody
    public String fetchProductsById(
            @PathVariable("product_id") Long productId) throws JsonProcessingException, NotFoundDataException {
        return mapperService.mapObjectToJson(productService.getProductById(productId));
    }

    /**
     * Return all products fitered by Category and Availability where 0 is false and 1 true
     *
     * @param category
     * @param availability
     * @return
     */
    @GetMapping
    @ApiOperation(value = "List Products", notes = "Service to list products by Category and availability")
    @ApiResponses(value = {@ApiResponse(code = 200, message = "List Products"),
            @ApiResponse(code = 400, message = "Products cannot be found")})
    @ResponseBody
    public String fetchProductsByCategoryAndAvailability(
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "availability", required = false) String availability) throws JsonProcessingException, UnsupportedEncodingException {

        return mapperService.mapObjectListToJson(productService.getProductByFilter(category, availability));
    }


}
