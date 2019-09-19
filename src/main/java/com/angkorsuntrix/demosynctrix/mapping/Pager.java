package com.angkorsuntrix.demosynctrix.mapping;

import java.util.ArrayList;
import java.util.List;

public class Pager<T> {
    private List<T> data = new ArrayList<>();
    private int size;
    private int totalPage;

    public Pager() {

    }

    public Pager(List<T> data, int size, int totalPage) {
        this.data = data;
        this.size = size;
        this.totalPage = totalPage;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
