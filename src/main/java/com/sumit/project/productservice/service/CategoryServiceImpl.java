package com.sumit.project.productservice.service;

import com.sumit.project.productservice.dto.*;
import com.sumit.project.productservice.entity.Category;
import com.sumit.project.productservice.mapper.CategoryMapper;
import com.sumit.project.productservice.repository.CategoryRepository;
import com.sumit.project.productservice.utilitiies.CategoryUtils;
import com.sumit.project.productservice.dto.CategoryResponseDtoList;
import com.sumit.project.productservice.dto.ResponseStatus;
import com.sumit.project.productservice.dto.CategoryRequestDto;
import com.sumit.project.productservice.dto.CategoryResponseDto;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService{

    private static final Logger logger= Logger.getLogger(CategoryServiceImpl.class);

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    CategoryUtils categoryUtils;

    @Autowired
    CategoryMapper categoryMapper;


    @Override
    public CategoryResponseDtoList saveCategories(List<CategoryRequestDto> categoryRequestDtoList) {
        logger.debug("Inside saveCategories()");
        CategoryResponseDtoList categoryResponseDtoList = new CategoryResponseDtoList();
        ResponseStatus responseStatus = new ResponseStatus();
        if(!CollectionUtils.isEmpty(categoryRequestDtoList)) {
            List<Category> categoryList = categoryMapper.mapCategoryRequestDtoToCategory(categoryRequestDtoList);
            categoryList = categoryUtils.validateCategories(categoryList);
            if(!CollectionUtils.isEmpty(categoryList)) {
                categoryList = categoryRepository.saveAll(categoryList);
                responseStatus.setStatus(StatusDto.CATEGORY_ADD_SUCCESS.statusMessage);
                logger.debug("Categories Saved.");
            }else{
                responseStatus.setStatus(StatusDto.CATEGORY_ADD_ERROR.statusMessage);
                responseStatus.setStatusMessage("Category validation failed.");
                logger.error("Category validation failed.");
            }
            List<CategoryResponseDto> categoryResponseDtos = categoryMapper.mapCategoryToCategoryResponseDto(categoryList);
            categoryResponseDtoList.setCategoryResponseDtoList(categoryResponseDtos);
        }else{
            responseStatus.setStatus(StatusDto.CATEGORY_ADD_ERROR.statusMessage);
            responseStatus.setStatusMessage("Category request empty.");
            logger.error("Category request empty.");
        }
        categoryResponseDtoList.setResponseStatus(responseStatus);
        return categoryResponseDtoList;
    }

    public CategoryResponseDtoList getCategories(){
        logger.debug("Inside getCategories()");
        CategoryResponseDtoList categoryResponseDtoList=new CategoryResponseDtoList();
        ResponseStatus responseStatus = new ResponseStatus();
        List<Category> categoryList=categoryRepository.findAll();
        if(categoryList!=null) {
            List<CategoryResponseDto> categoryResponseDtos = categoryMapper.mapCategoryToCategoryResponseDto(categoryList);
            categoryResponseDtoList.setCategoryResponseDtoList(categoryResponseDtos);
            responseStatus.setStatus(StatusDto.CATEGORY_GET_SUCCESS.statusMessage);
        }else{
            responseStatus.setStatus(StatusDto.CATEGORY_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage("Categories not present.");
        }
        categoryResponseDtoList.setResponseStatus(responseStatus);
        return categoryResponseDtoList;
    }
}
