package com.sumit.project.productservice.dto;

import java.util.List;

public class CategoryResponseDtoList {
    private List<CategoryResponseDto> categoryResponseDtoList;
    private ResponseStatus responseStatus;

    public List<CategoryResponseDto> getCategoryResponseDtoList() {
        return categoryResponseDtoList;
    }

    public void setCategoryResponseDtoList(List<CategoryResponseDto> categoryResponseDtoList) {
        this.categoryResponseDtoList = categoryResponseDtoList;
    }

    public ResponseStatus getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(ResponseStatus responseStatus) {
        this.responseStatus = responseStatus;
    }
}
