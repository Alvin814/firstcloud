package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class CreateTicketRequest {

    @ApiModelProperty(value = "工单ID", hidden = true)
    private String id;

    @ApiModelProperty(value = "工单名称", required = true)
    private String name;

    @ApiModelProperty(value = "工单模板ID", required = true)
    private String configId;

    @ApiModelProperty("工单内容")
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getConfigId() {
        return configId;
    }

    public void setConfigId(String configId) {
        this.configId = configId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
