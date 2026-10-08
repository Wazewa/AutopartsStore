package org.korolev.autopartsstore.api.compatibility.service;

import lombok.AllArgsConstructor;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityRequest;
import org.korolev.autopartsstore.api.compatibility.dto.CompatibilityResponse;
import org.korolev.autopartsstore.api.compatibility.entity.CompatibilityEntity;
import org.korolev.autopartsstore.api.product.entity.ProductEntity;
import org.korolev.autopartsstore.api.compatibility.exception.CompatibilityNotFoundException;
import org.korolev.autopartsstore.api.compatibility.mapper.CompatibilityMapper;
import org.korolev.autopartsstore.api.compatibility.repository.CompatibilityRepository;
import org.korolev.autopartsstore.api.product.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class CompatibilityService {

    private final CompatibilityRepository compatibilityRepository;
    private final CompatibilityMapper compatibilityMapper;
    private final ProductService productService;

    @Transactional(readOnly = true)
    public List<CompatibilityResponse> findAllCompatibilities() {
        return compatibilityRepository.findAll().stream()
                .map(compatibilityMapper::toResponse)
                .toList();
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
    public void deleteCompatibility(Long id) {
        if(!compatibilityRepository.existsById(id)) {
            throw new CompatibilityNotFoundException("Compatibility not found.");
        }
        compatibilityRepository.deleteById(id);
    }
}
