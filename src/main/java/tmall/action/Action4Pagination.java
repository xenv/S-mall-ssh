package tmall.action;

import tmall.util.Pagination;

/**
 * 处理Pagination的注入和取出
 */
public class Action4Pagination extends Action4Upload{
    protected Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}