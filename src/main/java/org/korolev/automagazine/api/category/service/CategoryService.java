package org.korolev.automagazine.api.category.service;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.category.dto.CategoryRequest;
import org.korolev.automagazine.api.category.dto.CategoryResponse;
import org.korolev.automagazine.api.category.entity.CategoryEntity;
import org.korolev.automagazine.api.category.exception.CategoryAlreadyExistsException;
import org.korolev.automagazine.api.category.exception.CategoryInUseException;
import org.korolev.automagazine.api.category.exception.CategoryNotFoundException;
import org.korolev.automagazine.api.category.mapper.CategoryMapper;
import org.korolev.automagazine.api.category.repository.CategoryRepository;
import org.korolev.automagazine.api.product.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public CategoryResponse findCategoryById(Long id) {
        return categoryMapper.toResponse(
                categoryRepository.findById(id)
                    .orElseThrow(
                    () -> new CategoryNotFoundException("Category not found.")
        ));
    }

    @Transactional
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryRequest);

        if(categoryRepository.existsByName(categoryEntity.getName())) {
            throw new CategoryAlreadyExistsException("Category already exists.");
        }

        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    @Transactional
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found."));

        if (!categoryRequest.name().equals(categoryEntity.getName())
                && categoryRepository.existsByName(categoryRequest.name())) {
            throw new CategoryAlreadyExistsException("Category with name " + categoryRequest.name() + " already exists");
        }

        categoryMapper.updateEntity(categoryRequest, categoryEntity);


        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    @Transactional
    public void deleteCategory(Long id) {
        if(!categoryRepository.existsById(id)) {
            throw new CategoryNotFoundException("Category not found.");
        }

        if (productRepository.existsByCategoryId(id)) {
            throw new CategoryInUseException(
                    "Cannot delete category with id " + id + " because it has products."
            );
        }

        categoryRepository.deleteById(id);
    }
}
