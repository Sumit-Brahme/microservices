package com.sumit.project.productservice.service;

import com.sumit.project.productservice.dto.CategoryRequestDto;
import com.sumit.project.productservice.dto.CategoryResponseDtoList;

import java.util.List;

public interface CategoryService {
    public CategoryResponseDtoList saveCategories(List<CategoryRequestDto> categoryList);
    public CategoryResponseDtoList getCategories();
}
