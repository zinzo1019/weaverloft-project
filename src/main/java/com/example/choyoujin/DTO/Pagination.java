package com.example.choyoujin.DTO;

import lombok.Data;

@Data
public class Pagination {
    private int totalCount;
    private int startPage;
    private int endPage;
    private boolean prev;
    private boolean next;
    private int displayPageNum = 5;
    private PageRequest pageRequest;

    public void setPageRequest(PageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcData();
    }
    public void calcData() {
        endPage = (int) (Math.ceil(pageRequest.getPage() / (double) displayPageNum) * displayPageNum);
        startPage = (endPage - displayPageNum);
        int lastPage = (int) (Math.ceil(totalCount / (double) pageRequest.getSizePerPage()));
        if (endPage > lastPage) {
            endPage = lastPage;
        }
        prev = startPage == 1 ? false : true;
        next = endPage * pageRequest.getSizePerPage() >= totalCount ? false : true;
    }
}
