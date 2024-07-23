package it.pagopa.selfcare.pagopa.util;

import it.pagopa.selfcare.pagopa.model.PageInfo;

/**
 * Contains utility methods related to the creation of a PageInfo instance
 */
public class PageInfoMapper {
    public static PageInfo toPageInfo(int page, int limit, long totalPages, long totalElements) {
        return PageInfo.builder().limit(limit).page(page).totalElements(totalElements).totalPages(totalPages).build();
    }
}
