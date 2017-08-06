/*
 * This source file is subject to the license that is bundled with this package in the file LICENSE.
 */
package com.montealegreluis.yelpv3.search;

public class Pagination {
    private static final int MAX_YELP_OFFSET = 1000;
    private final int pageSize;
    private final int total;
    private final int page;

    public boolean hasPages() {
        return total >= pageSize;
    }

    public int currentPage() {
        return page;
    }

    public boolean hasNext() {
        return page + 1 <= last();
    }

    public int next() {
        return page + 1;
    }

    public boolean hasPrevious() {
        return page > 1;
    }

    public int previous() {
        return page - 1;
    }

    public boolean isFirst() {
        return page == 1;
    }

    public int first() {
        return 1;
    }

    public boolean isLast() {
        return page == last();
    }

    public int last() {
        return exceedsAPILimit() ? maxCountAllowed() : pagesCount();
    }

    public boolean exceedsAPILimit() {
        return total > MAX_YELP_OFFSET;
    }

    private int maxCountAllowed() {
        return (int) Math.ceil((double) MAX_YELP_OFFSET / pageSize);
    }

    private int pagesCount() {
        return (int) Math.ceil((double) total / pageSize);
    }

    static Pagination fromSearch(SearchCriteria criteria, int total) {
        return new Pagination(criteria, total);
    }

    private Pagination(SearchCriteria criteria, int total) {
        pageSize = criteria.limit();
        this.total = total;
        page = normalize((criteria.offset() / criteria.limit()) + 1);
    }

    private int normalize(int page) {
        if (page < 1) return 1;
        if (page > last()) return last();
        return page;
    }
}
