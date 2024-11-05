package com.sparta.myselectshop.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@NoArgsConstructor
public class SearchParameter {
    private int page;
    private int size;
    private String sortBy;
    private boolean isAsc;

    public int getPage() {
        return page > 0 ? page - 1 : 0;
    }

    public void setIsAsc(boolean isAsc) {
        this.isAsc = isAsc;
    }

    public boolean getIsAsc() {
        return this.isAsc;
    }

    public Sort.Direction getSort() {
        return getIsAsc() ? Sort.Direction.ASC : Sort.Direction.DESC;
    }
}