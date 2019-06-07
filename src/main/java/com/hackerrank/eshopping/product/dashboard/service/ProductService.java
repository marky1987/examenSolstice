package com.hackerrank.eshopping.product.dashboard.service;

import com.hackerrank.eshopping.product.dashboard.dto.ProductDTO;
import com.hackerrank.eshopping.product.dashboard.exception.NotFoundDataException;
import com.hackerrank.eshopping.product.dashboard.model.Product;
import com.hackerrank.eshopping.product.dashboard.repository.IProductRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private IProductRepository iProductRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public ProductService(IProductRepository iProductRepository) {
        this.iProductRepository = iProductRepository;
    }

    /**
     * Create or Add a new Product
     *
     * @param product
     * @return
     */
    @Transactional
    public void createProduct(ProductDTO product) {

        Product productToCreate = new Product();
        productToCreate.setId(product.getId());
        productToCreate.setAvailability(product.getAvailability());
        productToCreate.setCategory(product.getCategory());
        productToCreate.setDiscountedPrice(product.getDiscountedPrice());
        productToCreate.setName(product.getName());
        productToCreate.setRetailPrice(product.getRetailPrice());

        entityManager.persist(productToCreate);
    }

    @Transactional
    public void updateProduct(Long productId, ProductDTO productDTO) {
        Product productToUpdate = iProductRepository.findById(productId);
        productToUpdate.setRetailPrice(productDTO.getRetailPrice());
        productToUpdate.setDiscountedPrice(productDTO.getDiscountedPrice());
        productToUpdate.setAvailability(productDTO.getAvailability());
        this.iProductRepository.saveAndFlush(productToUpdate);
    }

    public List<Product> getAllProducts() {
        return this.iProductRepository.findAll();
    }

    public Product getProductById(Long productId) throws NotFoundDataException {
        Product product = iProductRepository.findById(productId);
        if (product == null) {
            throw new NotFoundDataException("The id not found");
        }
        return product;
    }

    public List<Product> getProductByFilter(String categoryProduct, String availability) throws UnsupportedEncodingException {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Product> productQuery = criteriaBuilder.createQuery(Product.class);
        Root<Product> pRoot = productQuery.from(Product.class);

        List<Predicate> predicates = new ArrayList<>();
        if (categoryProduct != null || availability != null) {
            if (categoryProduct != null) {
                String category = URLDecoder.decode(categoryProduct, "UTF-8");
                predicates.add(criteriaBuilder.equal(pRoot.get("category"), category));
            }
            if (availability != null) {
                boolean available = false;
                if (Integer.parseInt(availability) == 1) {
                    available = true;
                }
                predicates.add(criteriaBuilder.equal(pRoot.get("availability"), available));
            }

            productQuery.where(predicates.toArray(new Predicate[0]));

            if (categoryProduct != null && availability == null) {
                productQuery.orderBy(criteriaBuilder.asc(pRoot.get("discountedPrice")));
            }
            if (categoryProduct != null && availability != null) {
                Expression<Number> diff = criteriaBuilder.diff(pRoot.get("retailPrice"), pRoot.get("discountedPrice"));
                Expression<Number> product = criteriaBuilder.prod(pRoot.get("retailPrice"), 100);
                Expression<Number> quot = criteriaBuilder.quot(diff, product);

                productQuery.orderBy(
                        criteriaBuilder.desc(quot),
                        criteriaBuilder.asc(pRoot.get("discountedPrice")),
                        criteriaBuilder.asc(pRoot.get("id")));

            }
        }

        return entityManager.createQuery(productQuery).getResultList();
    }
}
