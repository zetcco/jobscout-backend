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

import com.zetcco.jobscoutserver.domain.Skill;
import com.zetcco.jobscoutserver.domain.support.dto.CategoryDTO;
import com.zetcco.jobscoutserver.services.CategoryService;
import com.zetcco.jobscoutserver.services.support.exceptions.NotFoundException;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/")
    public ResponseEntity<List<CategoryDTO>> getAllCategories() throws NotFoundException {
        try {
            return new ResponseEntity<List<CategoryDTO>>(categoryService.getAllCategories(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<CategoryDTO>> getCategoryByContainingIgnoreType(@RequestParam("q") String name)
            throws NotFoundException {
        try {
            return new ResponseEntity<List<CategoryDTO>>(categoryService.getCategoryByNameContainingIgnoreCase(name),
                    HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long categoryId) throws NotFoundException {
        try {
            return new ResponseEntity<CategoryDTO>(categoryService.getCategoryById(categoryId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/")
    public ResponseEntity<CategoryDTO> saveCategory(@RequestBody CategoryDTO categoryDto) throws NotFoundException {
        try {
            return new ResponseEntity<CategoryDTO>(categoryService.addNewCategory(categoryDto), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long categoryId, @RequestBody CategoryDTO category)
            throws NotFoundException {
        try {
            if (categoryId != category.getId())
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Incorrect parameters");
            return new ResponseEntity<CategoryDTO>(categoryService.updateCategory(category), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{categoryId}/skills")
    public ResponseEntity<List<Skill>> getCategorySkills(@PathVariable Long categoryId) throws NotFoundException {
        try {
            return new ResponseEntity<List<Skill>>(categoryService.getSkillsByCategory(categoryId), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

}
