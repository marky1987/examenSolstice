package com.hackerrank.eshopping.product.dashboard.repository;

import com.hackerrank.eshopping.product.dashboard.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Interface to define operations into DB
 */
@Component
public interface IProductRepository extends JpaRepository<Product, Integer> {

    /**
     * Find Product by id
     *
     * @param id
     * @return
     */
    Product findById(Long id);

    /**
     * List products filtered by category and availability
     *
     * @param category
     * @param availability
     * @return
     */
    List<Product> findByCategoryAndAvailability(String category, boolean availability);

    /**
     * List products by category
     *
     * @param category
     * @return
     */
    List<Product> findByCategory(String category);

}
