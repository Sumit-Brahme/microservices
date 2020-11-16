package com.sumit.project.productservice.utilitiies;

import com.sumit.project.productservice.entity.Product;
import com.sumit.project.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductUtils {

    @Autowired
    ProductRepository productRepository;

    public List<Product> verfiyProducts(List<Long> productIdList){
        List<Product> verifiedProductList=productRepository.findAllById(productIdList);
        return verifiedProductList;
    }
}
