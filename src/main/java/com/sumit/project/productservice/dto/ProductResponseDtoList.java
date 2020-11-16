package com.sumit.project.productservice.dto;

import java.util.List;

public class ProductResponseDtoList {
    private List<ProductResponseDto> productResponseDtoList;
    private ResponseStatus responseStatus;

    public List<ProductResponseDto> getProductResponseDtoList() {
        return productResponseDtoList;
    }

    public void setProductResponseDtoList(List<ProductResponseDto> productResponseDtoList) {
        this.productResponseDtoList = productResponseDtoList;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
