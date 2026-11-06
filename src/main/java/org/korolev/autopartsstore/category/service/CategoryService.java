package org.korolev.autopartsstore.category.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.category.dto.CategoryRequest;
import org.korolev.autopartsstore.category.dto.CategoryResponse;
import org.korolev.autopartsstore.category.entity.CategoryEntity;
import org.korolev.autopartsstore.category.exception.CategoryAlreadyExistsException;
import org.korolev.autopartsstore.category.exception.CategoryInUseException;
import org.korolev.autopartsstore.category.exception.CategoryNotFoundException;
import org.korolev.autopartsstore.category.mapper.CategoryMapper;
import org.korolev.autopartsstore.category.repository.CategoryRepository;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.event.service.EventService;
import org.korolev.autopartsstore.product.repository.ProductRepository;
import org.korolev.autopartsstore.security.CurrentUserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final ProductRepository productRepository;
    private final CurrentUserService currentUserService;
    private final EventService eventService;

    @Transactional(readOnly = true)
    public List<CategoryResponse> findAllCategories() {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::toResponse)
                .toList();
    }

    @Transactional
    public CategoryResponse findCategoryById(Long id) {
        CategoryResponse categoryResponse = categoryMapper.toResponse(
                categoryRepository.findById(id)
                    .orElseThrow(
                    () -> new CategoryNotFoundException("Category not found.")
        ));

        eventService.trackEvent(EventType.CATEGORY_VIEW,
                currentUserService.getCurrentUserOrNull(), null);

        return categoryResponse;
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse createCategory(CategoryRequest categoryRequest) {

        CategoryEntity categoryEntity = categoryMapper.toEntity(categoryRequest);

        if(categoryRepository.existsByNameCategory(categoryEntity.getNameCategory())) {
            throw new CategoryAlreadyExistsException("Category already exists.");
        }

        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CategoryResponse updateCategory(Long id, CategoryRequest categoryRequest) {

        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(
                () -> new CategoryNotFoundException("Category not found."));

        if (!categoryRequest.nameCategory().equals(categoryEntity.getNameCategory())
                && categoryRepository.existsByNameCategory(categoryRequest.nameCategory())) {
            throw new CategoryAlreadyExistsException("Category with name " + categoryRequest.nameCategory() + " already exists");
        }

        categoryMapper.updateEntity(categoryRequest, categoryEntity);


        return categoryMapper.toResponse(categoryRepository.save(categoryEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
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
