package com.sumit.project.productservice.controller;

import com.sumit.project.productservice.dto.*;
import com.sumit.project.productservice.dto.ResponseStatus;
import com.sumit.project.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.sumit.project.productservice.dto.StatusDto.*;

@Controller
public class ProductController {

    @Autowired
    ProductService productService;

    @GetMapping("product/get")
    @ResponseBody
    public ResponseEntity getProducts(){
        ResponseStatus responseStatus=new ResponseStatus();
        ProductResponseDtoList productResponseDtoList=new ProductResponseDtoList();
        try{
            productResponseDtoList=productService.getProducts();
            return new ResponseEntity(productResponseDtoList,HttpStatus.OK);
        }
        catch(Exception e){
            responseStatus.setStatus(PRODUCT_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage(e.getMessage());
            productResponseDtoList.setResponseStatus(responseStatus);
            return new ResponseEntity(productResponseDtoList, HttpStatus.OK);
        }
    }

    @PostMapping("product/create")
    @ResponseBody
    public ResponseEntity saveProducts(@RequestBody ProductRequestDtoList productRequestDtoList) throws Exception {
        ProductResponseDtoList productResponseDtoList=new ProductResponseDtoList();
        ResponseStatus responseStatus=new ResponseStatus();
        try{
            if(productRequestDtoList!=null) {
                productResponseDtoList = productService.saveProducts(productRequestDtoList.getProductRequestDtoList());
            }else{
                responseStatus.setStatus(PRODUCT_GET_ERROR.statusMessage);
            }
            return new ResponseEntity(productResponseDtoList, HttpStatus.CREATED);
        }catch (Exception e){
            responseStatus.setStatusMessage(PRODUCT_GET_ERROR.statusMessage);
            responseStatus.setStatusMessage(e.getMessage());
            productResponseDtoList.setResponseStatus(responseStatus);
            return new ResponseEntity(responseStatus, HttpStatus.CREATED);
        }
    }

    @PostMapping("product/buy")
    @ResponseBody
    public ResponseEntity buyProducts(@RequestBody ProductRequestDtoList productRequestDtoList) throws Exception{
        ProductRelayResponse productRelayResponse=new ProductRelayResponse();
        ResponseStatus responseStatus=new ResponseStatus();
        try {
            if(productRequestDtoList!=null){
                productRelayResponse=productService.buyProducts(productRequestDtoList.getProductRequestDtoList());
            }else{
                responseStatus.setStatus(PRODUCT_BUY_ERROR.statusMessage);
                productRelayResponse.setResponseStatus(responseStatus);
            }
            return new ResponseEntity(productRelayResponse,HttpStatus.CREATED);
        }
        catch (Exception e){
            responseStatus.setStatusMessage(PRODUCT_BUY_ERROR.statusMessage);
            responseStatus.setStatus(e.getMessage());
            productRelayResponse.setResponseStatus(responseStatus);
            return new ResponseEntity(productRelayResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("product/verify")
    @ResponseBody
    public ResponseEntity verifyProducts(@RequestBody List<ProductRequestDto> productRequestDtoList){
        ProductRelayResponse productRelayResponse=new ProductRelayResponse();
        ResponseStatus responseStatus=new ResponseStatus();
        try {
            if(productRequestDtoList!=null) {
                productRelayResponse = productService.verifyProducts(productRequestDtoList);
            }else{
                responseStatus.setStatus(PRODUCT_VERIFY_ERROR.statusMessage);
            }
            return new ResponseEntity(productRelayResponse, HttpStatus.CREATED);
        }catch (Exception e){
            responseStatus.setStatusMessage(PRODUCT_VERIFY_ERROR.statusMessage);
            responseStatus.setStatusMessage(e.getMessage());
            productRelayResponse.setResponseStatus(responseStatus);
            return new ResponseEntity(productRelayResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}