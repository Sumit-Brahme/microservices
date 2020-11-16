package com.sumit.project.productservice.utilitiies;

import com.sumit.project.productservice.entity.Category;
import com.sumit.project.productservice.repository.CategoryRepository;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;


@Component
public class CategoryUtils {
    private static final Logger logger= Logger.getLogger(CategoryUtils.class);
    @Autowired
    CategoryRepository categoryRepository;

    public List<Category> validateCategories(List<Category> categoryList){
        logger.debug("Inside validateCategories()");
        List<Category> validCategoryList=new ArrayList<>();
        for(Category category:categoryList){
            if(category.getParent_category_id()!=null) {
                Category parentCategory = validateCategory(category.getParent_category_id());
                if (parentCategory != null) {
                    category.setParentCategory(parentCategory);
                    validCategoryList.add(category);
                }
            }
            else if(category.getName()!=null &&category.getDescription()!=null) {
                validCategoryList.add(category);
            }
        }
        return validCategoryList;
    }

    public Category validateCategory(long parentCategoryId){
        return categoryRepository.findById(parentCategoryId).orElse(null);
    }


}
