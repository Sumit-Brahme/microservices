package com.sumit.project.productservice.mapper;

import com.sumit.project.productservice.entity.Category;
import com.sumit.project.productservice.dto.CategoryRequestDto;
import com.sumit.project.productservice.dto.CategoryResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CategoryMapper {
    @Autowired
    ObjectMapperUtils objectMapperUtils;

    public List<Category> mapCategoryRequestDtoToCategory(List<CategoryRequestDto> categoryRequestDtoList){
        return objectMapperUtils.mapAll(categoryRequestDtoList,Category.class);
    }

    public List<CategoryResponseDto> mapCategoryToCategoryResponseDto(List<Category> categoryList){
        return objectMapperUtils.mapAll(categoryList,CategoryResponseDto.class);
    }
}
