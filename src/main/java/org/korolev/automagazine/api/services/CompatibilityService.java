package org.korolev.automagazine.api.services;

import lombok.AllArgsConstructor;
import org.korolev.automagazine.api.dto.CompatibilityRequest;
import org.korolev.automagazine.api.dto.CompatibilityResponse;
import org.korolev.automagazine.api.entities.CompatibilityEntity;
import org.korolev.automagazine.api.entities.ProductEntity;
import org.korolev.automagazine.api.exceptions.CompatibilityNotFoundException;
import org.korolev.automagazine.api.mappers.CompatibilityMapper;
import org.korolev.automagazine.api.repositories.CompatibilityRepository;
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
