package com.example.productservice.service.impl;

import com.example.productservice.enums.Language;
import com.example.productservice.exception.enums.FriendlyMessageCodes;
import com.example.productservice.exception.exceptions.ProductAlreadyDeletedException;
import com.example.productservice.exception.exceptions.ProductNotCreatedException;
import com.example.productservice.exception.exceptions.ProductNotFoundException;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.entity.Product;
import com.example.productservice.request.ProductCreateRequest;
import com.example.productservice.request.ProductUpdateRequest;
import com.example.productservice.service.IProductRepositoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductRepositoryServiceImpl implements IProductRepositoryService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(Language language, ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: ", this.getClass().getSimpleName(), productCreateRequest); //burada loga classın ismi methodun ismi ve request içeriğini yazmış olduk
        try {
            Product product = Product.builder()
                    .productName(productCreateRequest.getProductName())
                    .quantity(productCreateRequest.getQuantity())
                    .price(productCreateRequest.getPrice())
                    .deleted(false)
                    .build();
            Product productResponse = productRepository.save(product);
            log.debug("[{}][createProduct] -> response: ", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (Exception e) {
            throw new ProductNotCreatedException(language, FriendlyMessageCodes.PRODUCT_NOT_CREATED_EXCEPTION, "product request:" + productCreateRequest.toString());
        }
    }

    @Override
    public Product getProduct(Language language, Long productId) {
        log.debug("[{}][getProduct] -> request: ", this.getClass().getSimpleName(), productId);

        Product product = productRepository.getByProductIdAndDeletedFalse(productId);
        if (Objects.isNull(product)) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "product not found for product id:" + productId);
        }
        log.debug("[{}][getProduct] -> response: ", this.getClass().getSimpleName(), product);
        return product;

        //if yeni optional kütüphanesi kullanılırsa
        //  Optional<Product> optionalProduct = productRepository.findByProductIdAndDeletedFalse(productId);
        //  Product product = optionalProduct.orElseThrow(() ->
        //           new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "product not found for product id:" + productId));


    }


    @Override
    public List<Product> getProducts(Language language) {
        log.debug("[{}][getProducts] -> request: {}", this.getClass().getSimpleName(), language);
        List<Product> products = productRepository.getAllByDeletedFalse();
        if (products.isEmpty()) {
            throw new ProductNotFoundException(language, FriendlyMessageCodes.PRODUCT_NOT_FOUND_EXCEPTION, "no products found");
        }
        log.debug("[{}][getProducts] -> response: {}", this.getClass().getSimpleName(), products);
        return products;
    }

    @Override
    public Product updateProduct(Language language, Long productId, ProductUpdateRequest productUpdateRequest) {
        log.debug("[{}][updateProduct] -> request: ", this.getClass().getSimpleName(), productUpdateRequest);

        Product product = getProduct(language, productId);
        product.setProductName(productUpdateRequest.getProductName());
        product.setQuantity(productUpdateRequest.getQuantity());
        product.setPrice(productUpdateRequest.getPrice());
        product.setProductCreatedDate(product.getProductCreatedDate());
        product.setProductUpdatedDate(new Date());
        Product productResponse = productRepository.save(product);
        log.debug("[{}][updateProduct] -> response: ", this.getClass().getSimpleName(), productResponse);
        return productResponse;
    }

    @Override
    public Product deleteProduct(Language language, Long productId) {
        log.debug("[{}][deleteProduct] -> request: ", this.getClass().getSimpleName(), productId);
        Product product;
        try {
            product = getProduct(language, productId);
            product.setDeleted(true);
            product.setProductCreatedDate(new Date());
            Product productResponse = productRepository.save(product);
            log.debug("[{}][deleteProduct] -> response: ", this.getClass().getSimpleName(), productResponse);
            return productResponse;
        } catch (Exception e) {
            throw new ProductAlreadyDeletedException(language, FriendlyMessageCodes.PRODUCT_ALREADY_DELETED, "Product already deleted product id: " + productId);
        }
    }
}
