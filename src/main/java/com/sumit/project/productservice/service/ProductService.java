package com.sumit.project.productservice.service;

import com.sumit.project.productservice.dto.ProductRelayResponse;
import com.sumit.project.productservice.dto.ProductRequestDto;
import com.sumit.project.productservice.dto.ProductResponseDtoList;

import java.util.List;

public interface ProductService {
    public ProductResponseDtoList saveProducts(List<ProductRequestDto> productRequestDtoList) throws Exception;
    public ProductResponseDtoList getProducts();
    public ProductRelayResponse buyProducts(List<ProductRequestDto> productRequestDtoList) throws Exception;
    public ProductRelayResponse verifyProducts(List<ProductRequestDto> productRequestDtoList);
}
