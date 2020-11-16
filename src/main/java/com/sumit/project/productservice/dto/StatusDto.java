package com.sumit.project.productservice.dto;

public enum StatusDto {
    CATEGORY_ADD_SUCCESS("Categories Added Successfully."),
    CATEGORY_ADD_ERROR("Error Occurred While Adding Categories."),
    CATEGORY_GET_SUCCESS("Categories Fetched Successfully."),
    CATEGORY_GET_ERROR("Error Occurred While Fetching Categories."),
    PRODUCT_ADD_SUCCESS("Products Added Successfully."),
    PRODUCT_ADD_ERROR("Error Occurred While Adding Products."),
    PRODUCT_GET_SUCCESS("Products Fetched Successfully."),
    PRODUCT_GET_ERROR("Error Occurred While Fetching Products."),
    PRODUCT_BUY_SUCCESS("Products Buying Successful."),
    PRODUCT_BUY_ERROR("Error Occurred While Buying Products."),
    PRODUCT_VERIFY_SUCCESS("Products Verification Successful."),
    PRODUCT_VERIFY_ERROR("Error Occurred While Verifying Products.");

    public final String statusMessage;

    StatusDto(String statusMessage) {
        this.statusMessage=statusMessage;
    }

}
