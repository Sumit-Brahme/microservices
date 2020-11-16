package com.sumit.project.productservice.controller;

import com.sumit.project.productservice.dto.CategoryRequestDtoList;
import com.sumit.project.productservice.dto.CategoryResponseDtoList;
import com.sumit.project.productservice.dto.ResponseStatus;
import com.sumit.project.productservice.dto.StatusDto;
import com.sumit.project.productservice.service.CategoryService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CategoryController {
    private static final Logger logger= Logger.getLogger(CategoryController.class);

    @Autowired
    CategoryService categoryService;

    @PostMapping("/category/create")
    @ResponseBody
    public ResponseEntity createCategory(@RequestBody CategoryRequestDtoList categoryRequestDtoList){
        logger.debug("Inside createCategory()");
        CategoryResponseDtoList categoryResponseDtoList =null;
        ResponseStatus responseStatus = new ResponseStatus();
        try {
            if(categoryRequestDtoList!=null) {
                categoryResponseDtoList = categoryService.saveCategories(categoryRequestDtoList.getCategoryRequestDtoList());
            }else{
                responseStatus.setStatusMessage(StatusDto.CATEGORY_ADD_ERROR.statusMessage);
            }
            return new ResponseEntity(categoryResponseDtoList, HttpStatus.CREATED);
        }
        catch(Exception e){
            responseStatus.setStatusMessage(StatusDto.CATEGORY_ADD_ERROR.statusMessage);
            responseStatus.setStatusMessage(e.getMessage());
            logger.error("Error occurred while creating category.");
            return new ResponseEntity(responseStatus, HttpStatus.OK);
        }

    }

    @GetMapping("/category/get")
    @ResponseBody
    public ResponseEntity getCategory(){
        logger.debug("Inside getCategory()");
        CategoryResponseDtoList categoryResponseDtoList =null;
        ResponseStatus responseStatus=new ResponseStatus();
        try {
            categoryResponseDtoList = categoryService.getCategories();
            return ResponseEntity.ok().body(categoryResponseDtoList);
        }
        catch (Exception e){
            responseStatus.setStatus(StatusDto.CATEGORY_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage(e.getMessage());
            logger.error("Error occurred while creating category.");
            return new ResponseEntity(responseStatus, HttpStatus.OK);
        }

    }

}
