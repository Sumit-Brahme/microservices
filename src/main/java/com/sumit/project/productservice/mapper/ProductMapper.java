package com.sumit.project.productservice.mapper;

import com.sumit.project.productservice.dto.ProductRequestDto;
import com.sumit.project.productservice.dto.ProductResponseDto;
import com.sumit.project.productservice.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductMapper {
    @Autowired
    ObjectMapperUtils objectMapperUtils;

    public List<Product> mapProductRequestDtoToProduct(List<ProductRequestDto> productRequestDtoList){
        return objectMapperUtils.mapAll(productRequestDtoList,Product.class);
    }

    public List<ProductResponseDto> mapProductToProductResponseDto(List<Product> productList){
        return objectMapperUtils.mapAll(productList,ProductResponseDto.class);
    }
}
