package com.sumit.project.productservice.service;

import com.sumit.project.productservice.dto.*;
import com.sumit.project.productservice.entity.Category;
import com.sumit.project.productservice.entity.Product;
import com.sumit.project.productservice.mapper.ProductMapper;
import com.sumit.project.productservice.repository.CategoryRepository;
import com.sumit.project.productservice.repository.ProductRepository;
import com.sumit.project.productservice.utilitiies.CategoryUtils;
import com.sumit.project.productservice.utilitiies.ProductUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger logger = Logger.getLogger(ProductServiceImpl.class);

    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ProductMapper productMapper;

    @Autowired
    CategoryUtils categoryUtils;

    @Autowired
    ProductUtils productUtils;

    private boolean isExceptionOccurred;

    @Override
    public ProductResponseDtoList saveProducts(List<ProductRequestDto> productRequestDtoList) throws Exception {
        logger.debug("Inside saveProducts()");
        ResponseStatus responseStatus = new ResponseStatus();
        ProductResponseDtoList productResponseDtoList = new ProductResponseDtoList();
        List<Product> savedProductsList = new ArrayList<>();
        List<Product> productList = productMapper.mapProductRequestDtoToProduct(productRequestDtoList);
        for (Product product : productList) {
            if (product.getCategoryId() != null) {
                Category category = categoryUtils.validateCategory(product.getCategoryId());
                if (category != null) {
                    product.setCategory(category);
                    Product savedProduct = productRepository.save(product);
                    savedProductsList.add(savedProduct);
                    responseStatus.setStatus(StatusDto.PRODUCT_ADD_SUCCESS.statusMessage);
                } else {
                    responseStatus.setStatus(StatusDto.PRODUCT_ADD_ERROR.statusMessage);
                    responseStatus.setStatusMessage("Category Validation failed.");
                    logger.error("Category Validation failed for a product: " + product.getId());
                }
            } else {
                responseStatus.setStatus(StatusDto.PRODUCT_ADD_ERROR.statusMessage);
                responseStatus.setStatusMessage("Category ID is not present.");
                logger.error("Category ID is not present for a product: " + product.getId());
            }
        }
        if(!CollectionUtils.isEmpty(savedProductsList)) {
            List<ProductResponseDto> productResponseDtos = productMapper.mapProductToProductResponseDto(savedProductsList);
            productResponseDtoList.setProductResponseDtoList(productResponseDtos);
        }
        productResponseDtoList.setResponseStatus(responseStatus);
        return productResponseDtoList;
    }

    @Override
    public ProductResponseDtoList getProducts() {
        logger.debug("Inside getProducts()");
        ProductResponseDtoList productResponseDtoList = new ProductResponseDtoList();
        ResponseStatus responseStatus = new ResponseStatus();
        List<Product> productList = productRepository.findAll();
        if (productList != null) {
            List<ProductResponseDto> productResponseDtos = productMapper.mapProductToProductResponseDto(productList);
            productResponseDtoList.setProductResponseDtoList(productResponseDtos);
            responseStatus.setStatus(StatusDto.PRODUCT_GET_SUCCESS.statusMessage);
        } else {
            responseStatus.setStatus(StatusDto.PRODUCT_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage("Products not present.");
        }
        productResponseDtoList.setResponseStatus(responseStatus);
        return productResponseDtoList;
    }

    public ProductRelayResponse buyProducts(List<ProductRequestDto> productRequestDtoList) throws Exception {
        logger.debug("Inside getProducts()");
        ProductRelayResponse productRelayResponse = new ProductRelayResponse();
        ResponseStatus responseStatus = new ResponseStatus();
        List<Product> productList = new ArrayList<>();
        Map<Long, Integer> productIdQuantityMap = new HashMap<>();
        List<Product> validProductList = new ArrayList<>();
        List<Long> productIdList = productRequestDtoList.stream().map(ProductRequestDto::getId).collect(Collectors.toList());
        List<Product> verifiedProductList = productUtils.verfiyProducts(productIdList);
        if (!CollectionUtils.isEmpty(verifiedProductList)) {
            productIdQuantityMap = productRequestDtoList.stream().collect(Collectors.toMap(ProductRequestDto::getId, ProductRequestDto::getQuantity));//productRequestDto
            productIdQuantityMap.forEach((key, value) -> {
                verifiedProductList.forEach(product -> {
                    if (key == product.getId() && value <= product.getQuantity()) {
                        product.setQuantity(product.getQuantity() - value);
                        productList.add(product);
                        logger.debug("Product added in productList: " + product.getId());
                    }

                });
            });
            if (!productList.containsAll(verifiedProductList)) {
                responseStatus.setStatus(StatusDto.PRODUCT_BUY_ERROR.statusMessage);
                productRelayResponse.setResponseStatus(responseStatus);productRelayResponse.setResponseStatus(responseStatus);
                responseStatus.setStatusMessage("Exception occurred while updating Product in productList.");
                logger.error("Exception occurred while updating Product in productList.");
            }else{
                if (!CollectionUtils.isEmpty(productList)) {
                    productRepository.saveAll(productList);
                    logger.debug("Product saved.");
                    validProductList = productList.stream().collect(Collectors.toList());
                    Map<Long, Integer> finalProductIdQuantityMap = productIdQuantityMap;
                    validProductList.forEach(product -> {
                        product.setQuantity(finalProductIdQuantityMap.get(product.getId()));
                    });
                    productRelayResponse.setProductList(validProductList);
                    responseStatus.setStatus(StatusDto.PRODUCT_BUY_SUCCESS.statusMessage);
                    productRelayResponse.setResponseStatus(responseStatus);
                } else {
                    responseStatus.setStatus(StatusDto.PRODUCT_BUY_ERROR.statusMessage);
                    responseStatus.setStatusMessage("Exception occurred while updating Product in productList.");
                    productRelayResponse.setResponseStatus(responseStatus);
                }
            }

        } else {
            responseStatus.setStatus(StatusDto.PRODUCT_BUY_ERROR.statusMessage);
            responseStatus.setStatusMessage("Products verification failed.");
            productRelayResponse.setResponseStatus(responseStatus);
        }
        return productRelayResponse;
    }


    public ProductRelayResponse verifyProducts(List<ProductRequestDto> productRequestDtoList) {
        List<Product> verifiedProductList = null;
        ResponseStatus responseStatus = new ResponseStatus();
        ProductRelayResponse productRelayResponse = new ProductRelayResponse();
        List<Long> productIdList = productRequestDtoList.stream().map(ProductRequestDto::getId).collect(Collectors.toList());
        if (!CollectionUtils.isEmpty(productIdList)) {
            verifiedProductList = productUtils.verfiyProducts(productIdList);
            if (!CollectionUtils.isEmpty(verifiedProductList)) {
                productRelayResponse.setProductList(verifiedProductList);
                responseStatus.setStatus(StatusDto.PRODUCT_VERIFY_SUCCESS.statusMessage);
                productRelayResponse.setResponseStatus(responseStatus);
            } else {
                responseStatus.setStatus(StatusDto.PRODUCT_VERIFY_ERROR.statusMessage);
                productRelayResponse.setResponseStatus(responseStatus);
            }
        } else {
            responseStatus.setStatus(StatusDto.PRODUCT_VERIFY_ERROR.statusMessage);
            productRelayResponse.setResponseStatus(responseStatus);
        }
        return productRelayResponse;
    }
}
