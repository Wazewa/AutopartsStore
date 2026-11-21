package org.korolev.autopartsstore.category.service;

import lombok.RequiredArgsConstructor;
import org.korolev.autopartsstore.category.dto.CategoryResponse;
import org.korolev.autopartsstore.event.entity.EventType;
import org.korolev.autopartsstore.event.service.EventService;
import org.korolev.autopartsstore.security.CurrentUserService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryFacade {
    private final CategoryService categoryService;
    private final EventService eventService;
    private final CurrentUserService currentUserService;

    public CategoryResponse getCategory(Long id){
        CategoryResponse categoryResponse = categoryService.findCategoryById(id);

        eventService.trackEvent(EventType.CATEGORY_VIEW,
                currentUserService.getCurrentUserOrNull(), null);

        return categoryResponse;
    }
}
