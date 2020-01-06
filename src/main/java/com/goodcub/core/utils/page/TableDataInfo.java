package com.goodcub.core.utils.page;

/**
 * @Author Luo.z.x
 * @Description: TODO
 * @Date 2020/1/6
 * @Version V1.0
 **/
import java.io.Serializable;
import java.util.List;

/**
 * 表格分页数据对象
 *
 * @author liuyong
 */
public class TableDataInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    //当前页
    private int pageNum;

    //每页的数量
    private int pageSize;

    //总页数
    private int pages;

    /** 总记录数 */
    private long total;

    /** 列表数据 */
    private List<?> items;

    /**
     * 表格数据对象
     */
    public TableDataInfo() { }

    /**
     * 分页
     *
     * @param items  列表数据
     * @param total 总记录数
     */
    public TableDataInfo(List<?> items, int total) {
        this.items = items;
        this.total = total;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List<?> getItems() {
        return items;
    }

    public void setItems(List<?> items) {
        this.items = items;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }
}

