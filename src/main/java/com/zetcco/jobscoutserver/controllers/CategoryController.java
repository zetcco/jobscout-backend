package com.zetcco.jobscoutserver.controllers;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.zetcco.jobscoutserver.domain.Category;
import com.zetcco.jobscoutserver.services.CategoryService;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<Category>> getAllCategories(){
        try {
            return new ResponseEntity<List<Category>>(categoryService.getAllCategories(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<Category>> getCategoryByContainingIgnoreType(@RequestParam("q") String name){
        try{
           return new ResponseEntity<List<Category>>(categoryService.getCategoryByNameContainingIgnoreCase(name) , HttpStatus.OK); 
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }      
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long categoryId){
        try{
            return new ResponseEntity<Category>(categoryService.getCategoryById(categoryId) , HttpStatus.OK); 
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }    
    }

    @PostMapping("/")
    public ResponseEntity<Category> saveCategory(@RequestBody Category category){ 
        try{
            return new ResponseEntity<Category>(categoryService.addNewCategory(category) , HttpStatus.OK);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }    
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long categoryId, @RequestBody Category category){
        try{
            return new ResponseEntity<Category>(categoryService.updateCategory(categoryId, category), HttpStatus.OK);
        }catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }       
    }

}
