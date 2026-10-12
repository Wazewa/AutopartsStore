package org.korolev.autopartsstore.api.product.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.product.dto.ProductFilter;
import org.korolev.autopartsstore.api.product.dto.ProductRequest;
import org.korolev.autopartsstore.api.product.dto.ProductResponse;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.product.exception.ProductAlreadyExistsException;
import org.korolev.autopartsstore.api.product.exception.ProductNotFoundException;
import org.korolev.autopartsstore.api.product.mapper.ProductMapper;
import org.korolev.autopartsstore.api.product.repository.ProductRepository;
import org.korolev.autopartsstore.api.product.util.ProductSpecifications;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional(readOnly = true)
    public PageResponse<ProductResponse> findAllProducts(Pageable pageable, ProductFilter productFilter) {
        Specification<ProductEntity> spec = ProductSpecifications.from(productFilter);
        return PageResponse.of(productRepository.findAll(spec, pageable), productMapper::toResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponse findProductById(Long id) {
        return productMapper.toResponse(
                productRepository.findById(id)
                        .orElseThrow(
                         () -> new ProductNotFoundException("Product not found.")
            )
        );
    }

    @Transactional(readOnly = true)
    public ProductEntity findEntityById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not found with id: " + id));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse createProduct(ProductRequest productRequest) {

        ProductEntity productEntity = productMapper.toEntity(productRequest);

        if(productRequest.article() != null && productRepository.existsByArticle(productEntity.getArticle()))  {
            throw new ProductAlreadyExistsException("Product already exists.");
        }
        return productMapper.toResponse(productRepository.save(productEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public ProductResponse updateProduct(Long id, ProductRequest productRequest) {

        ProductEntity productEntity = productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found.")
        );

        if (productRequest.article() != null
                && !productRequest.article().equals(productEntity.getArticle())
                && productRepository.existsByArticle(productRequest.article())) {
            throw new ProductAlreadyExistsException("Product with article " + productRequest.article() + " already exists");
        }

        productMapper.updateEntity(productRequest, productEntity);

        return productMapper.toResponse(productRepository.save(productEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteProduct(Long id) {
        if(!productRepository.existsById(id)) {
            throw new ProductNotFoundException("Product not found.");
        }
        productRepository.deleteById(id);
    }
}
