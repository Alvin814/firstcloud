package com.fit2cloud.ticket.dao.model.ext;

import com.fit2cloud.ticket.dao.model.TicketConfig;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ExtTicketConfig extends TicketConfig {

    @ApiModelProperty("工单模板标签")
    private List<String> tags;

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
