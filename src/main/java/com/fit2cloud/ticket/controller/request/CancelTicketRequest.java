package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

public class CancelTicketRequest {

    @ApiModelProperty(value = "工单ID", required = true)
    private String ticketId;

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }
}
