package com.example.choyoujin.DTO;

import lombok.Data;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Data
public class PageRequest {
    private int page;
    private int sizePerPage;

    public PageRequest() {
        this.page = 1;
        this.sizePerPage = 5;
    }

    public void setPage(int page) {
        if (page <= 0) {
            this.page = 1;
            return;
        }
        this.page = page;
    }

    public int getPageStart() {
        return (this.page - 1) * sizePerPage;
    }

    public String toUriStringByPage(int page) {
        UriComponents uriComponents = UriComponentsBuilder
                .newInstance()
                .queryParam("page", page)
                .build();
        return uriComponents.toUriString();
    }

    public String toUriString() {
        return toUriStringByPage(this.page);
    }
}
