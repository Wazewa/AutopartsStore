package org.korolev.autopartsstore.api.compatibility.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityFilter;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityRequest;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityResponse;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.korolev.autopartsstore.api.compatibility.util.CompatibilitySpecifications;
import org.korolev.autopartsstore.api.dto.PageResponse;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.compatibility.exception.CompatibilityNotFoundException;
import org.korolev.autopartsstore.api.compatibility.mapper.CompatibilityMapper;
import org.korolev.autopartsstore.api.compatibility.repository.CompatibilityRepository;
import org.korolev.autopartsstore.api.product.service.ProductService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CompatibilityService {

    private final CompatibilityRepository compatibilityRepository;
    private final CompatibilityMapper compatibilityMapper;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public PageResponse<CompatibilityResponse> findAllCompatibilities(Pageable pageable,
                                                                      CompatibilityFilter compatibilityFilter) {
        Specification<CompatibilityEntity> spec = CompatibilitySpecifications.from(compatibilityFilter);
        return PageResponse.of(compatibilityRepository.findAll(spec, pageable),
                (compatibilityMapper::toResponse));
    }

    @Transactional(readOnly = true)
    public CompatibilityResponse findCompatibilityById(Long id) {
        return compatibilityMapper.toResponse(
                compatibilityRepository.findById(id)
                        .orElseThrow(
                        () -> new CompatibilityNotFoundException("Compatibility not found.")
        ));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CompatibilityResponse createCompatibility(CompatibilityRequest compatibilityRequest) {

        if (compatibilityRequest.yearStart() != null && compatibilityRequest.yearEnd() != null
                && compatibilityRequest.yearStart() > compatibilityRequest.yearEnd()) {
            throw new IllegalArgumentException("yearStart must be <= yearEnd.");
        }

        ProductEntity product = productService.findEntityById(compatibilityRequest.productId());
        CompatibilityEntity compatibilityEntity = compatibilityMapper.toEntity(compatibilityRequest);
        compatibilityEntity.setProduct(product);

        return compatibilityMapper.toResponse(compatibilityRepository.save(compatibilityEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public CompatibilityResponse updateCompatibility(Long id, CompatibilityRequest compatibilityRequest) {

        if (compatibilityRequest.yearStart() != null && compatibilityRequest.yearEnd() != null
                && compatibilityRequest.yearStart() > compatibilityRequest.yearEnd()) {
            throw new IllegalArgumentException("yearStart must be <= yearEnd.");
        }

        CompatibilityEntity compatibilityEntity = compatibilityRepository.findById(id).orElseThrow(
                () -> new CompatibilityNotFoundException("Compatibility not found.")
        );

        ProductEntity product = productService.findEntityById(compatibilityRequest.productId());
        compatibilityMapper.updateEntity(compatibilityRequest, compatibilityEntity);
        compatibilityEntity.setProduct(product);

        return compatibilityMapper.toResponse(compatibilityRepository.save(compatibilityEntity));
    }

    @Transactional
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCompatibility(Long id) {
        if(!compatibilityRepository.existsById(id)) {
            throw new CompatibilityNotFoundException("Compatibility not found.");
        }
        compatibilityRepository.deleteById(id);
    }
}
