package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class ListTicketConfigRequest {

    @ApiModelProperty(value = "模糊查询: 工单模板名称或工单模板描述", hidden = true)
    private String quickSearch;

    @ApiModelProperty(value = "模糊查询: 工单模板名称")
    private String name;

    @ApiModelProperty(value = "模糊查询: 工单模板描述")
    private String description;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
