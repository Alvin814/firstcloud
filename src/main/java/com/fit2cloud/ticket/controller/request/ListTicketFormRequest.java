package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ListTicketFormRequest {

    @ApiModelProperty(value = "模糊查询: 表单名称或表单ID", hidden = true)
    private String quickSearch;

    @ApiModelProperty(value = "模糊查询: 表单名称")
    private String name;

    @ApiModelProperty(value = "排序", hidden = true)
    private String sort;

    public String getQuickSearch() {
        return quickSearch;
    }

    public void setQuickSearch(String quickSearch) {
        this.quickSearch = quickSearch;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
