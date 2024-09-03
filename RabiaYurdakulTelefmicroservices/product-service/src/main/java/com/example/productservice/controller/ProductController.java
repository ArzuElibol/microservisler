package com.example.productservice.controller;

import com.example.productservice.enums.Language;
import com.example.productservice.exception.enums.FriendlyMessageCodes;
import com.example.productservice.exception.utils.FriendlyMessageUtils;
import com.example.productservice.repository.ProductRepository;
import com.example.productservice.repository.entity.Product;
import com.example.productservice.request.ProductCreateRequest;
import com.example.productservice.request.ProductUpdateRequest;
import com.example.productservice.response.FriendlyMessage;
import com.example.productservice.response.InternalApiResponse;
import com.example.productservice.response.ProductResponse;
import com.example.productservice.service.IProductRepositoryService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping(value = "/api/1.0/product")
@RequiredArgsConstructor
class ProductController {
    private final IProductRepositoryService productRepositoryService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{language}/create")
    public InternalApiResponse<ProductResponse> createProduct(@PathVariable("language") Language language,
                                                              @RequestBody ProductCreateRequest productCreateRequest) {
        log.debug("[{}][createProduct] -> request: ", this.getClass().getSimpleName(), productCreateRequest);
        Product product = productRepositoryService.createProduct(language, productCreateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][createProduct] -> response: ", this.getClass().getSimpleName(), productResponse);

        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .descrption(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_CREATED))
                        .build())
                .httpStatus(HttpStatus.CREATED)
                .hasError(false)
                .payload(productResponse)
                .build();
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/get/{productId}")
    public InternalApiResponse<ProductResponse> getProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId) {
        log.debug("[{}][getProduct] -> request: ", this.getClass().getSimpleName(), language, productId);
        Product product = productRepositoryService.getProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][getProduct] -> response: ", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
//get methodunda friendly mesajı setlemedik. çünkü datalarda herhangi bir değişiklik yapmadık.

    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping(value = "/{language}/update/{productId}")
    public InternalApiResponse<ProductResponse> updateProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId, @RequestBody ProductUpdateRequest productUpdateRequest) {

        log.debug("[{}][updateProduct] -> request: ", this.getClass().getSimpleName(), productId, productUpdateRequest);

        Product product = productRepositoryService.updateProduct(language, productId, productUpdateRequest);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][updateProduct] -> response: ", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .descrption(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_UPDATED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @Operation(summary = "This endpoint get all product.")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/{language}/getAllProducts")
    public InternalApiResponse<List<ProductResponse>> getProducts(@PathVariable("language") Language language) {
        log.debug("[{}][getAllProducts] -> request: ", this.getClass().getSimpleName(), language);
        List<Product> products = productRepositoryService.getProducts(language);
        List<ProductResponse> productResponse = convertProductResponseList(products);
        log.debug("[{}][getAllProducts] -> response: {}", this.getClass().getSimpleName(), productResponse);
        return InternalApiResponse.<List<ProductResponse>>builder()
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping(value = "/{language}/delete/{productId}")
    public InternalApiResponse<ProductResponse> deleteProduct(@PathVariable("language") Language language, @PathVariable("productId") Long productId) {
        log.debug("[{}][deleteProduct] -> request productId: {}", this.getClass().getSimpleName(), language, productId);
        Product product = productRepositoryService.deleteProduct(language, productId);
        ProductResponse productResponse = convertProductResponse(product);
        log.debug("[{}][deleteProduct] -> response productId: {} ", this.getClass().getSimpleName(), productResponse, productId);
        return InternalApiResponse.<ProductResponse>builder()
                .friendlyMessage(FriendlyMessage.builder()
                        .title(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.SUCCESS))
                        .descrption(FriendlyMessageUtils.getFriendlyMessage(language, FriendlyMessageCodes.PRODUCT_SUCCESSFULLY_DELETED))
                        .build())
                .httpStatus(HttpStatus.OK)
                .hasError(false)
                .payload(productResponse)
                .build();
    }

    private List<ProductResponse> convertProductResponseList(List<Product> productList) {
        return productList.stream()
                .map(arg -> ProductResponse.builder()
                        .productId(arg.getProductId())
                        .productName(arg.getProductName())
                        .quantity(arg.getQuantity())
                        .price(arg.getPrice())
                        .productCreatedDate(arg.getProductCreatedDate().getTime())
                        .productUpdatedDate(arg.getProductUpdatedDate().getTime())
                        .build())
                .collect(Collectors.toList());
    }

    private static ProductResponse convertProductResponse(Product product) {
        return ProductResponse.builder()
                .productId(product.getProductId())
                .productName(product.getProductName())
                .quantity(product.getQuantity())
                .price(product.getPrice())
                .productCreatedDate(product.getProductCreatedDate().getTime())
                .productUpdatedDate(product.getProductUpdatedDate().getTime())
                .build();
    }


}
