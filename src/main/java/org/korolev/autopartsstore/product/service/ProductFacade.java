package org.korolev.autopartsstore.product.service;

import lombok.RequiredArgsConstructor;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.event.service.EventService;
import org.korolev.autopartsstore.product.dto.ProductResponse;
import org.korolev.autopartsstore.security.CurrentUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductFacade {
    private final EventService eventService;
    private final CurrentUserService currentUserService;
    private final ProductService productService;

    public ProductResponse getProduct(Long id) {
        ProductResponse productResponse = productService.findProductById(id);

        eventService.trackEvent(EventType.PRODUCT_VIEW,
                currentUserService.getCurrentUserOrNull(), null);

        return productResponse;
    }
}
