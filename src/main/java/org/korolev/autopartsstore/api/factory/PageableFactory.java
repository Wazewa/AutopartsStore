package org.korolev.autopartsstore.api.factory;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;

import java.util.Set;

public class PageableFactory {
    public static Pageable create(int page, int size, String sortBy, String direction,
                                  Set<String> allowedSortFields) {
        int safePage = Math.max(page, 0);
        int safeSize = Math.clamp(size, 1, 1024);
        String safeSortBy = (allowedSortFields != null && allowedSortFields.contains(sortBy))
                ? sortBy
                : "id";   // дефолт
        Sort.Direction dir = "desc".equalsIgnoreCase(direction) ? Sort.Direction.DESC : Sort.Direction.ASC;
        return PageRequest.of(safePage, safeSize, Sort.by(dir, safeSortBy));
    }
}
