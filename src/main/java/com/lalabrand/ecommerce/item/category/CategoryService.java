package com.lalabrand.ecommerce.item.category;

import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<CategoryDTO> findAllCategory() {
        try {
            List<Category> categories = categoryRepository.findAll();
            if (categories.isEmpty()) {
                throw new EntityNotFoundException("No categories found");
            }
            return categories.stream()
                    .map(CategoryDTO::fromEntity)
                    .collect(Collectors.toList());
        } catch (EntityNotFoundException ex) {
            logger.info("No categories found: {}", ex.getMessage());
            throw ex;
        } catch (Exception ex) {
            logger.info("Error occurred while fetching categories: {}", ex.getMessage());
            throw ex;
        }
    }
}
