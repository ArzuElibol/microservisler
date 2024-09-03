package com.example.productservice.response;

import com.example.productservice.request.ProductCreateRequest;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ProductResponse {
    private Long productId;
    private String productName;
    private Double price;
    private Integer quantity;
    private Long productCreatedDate;
    private Long productUpdatedDate;
}
