package com.sumit.project.productservice.dto;

import com.sumit.project.productservice.entity.Product;

import java.util.List;

public class ProductRelayResponse {
    private List<Product> productList;
    private ResponseStatus responseStatus;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
