package com.fit2cloud.ticket.controller.request;

import com.fit2cloud.ticket.dao.model.Ticket;
import io.swagger.annotations.ApiModelProperty;

public class ApproveTicketRequest {

    @ApiModelProperty(value = "任务ID", required = true)
    private String taskId;
    @ApiModelProperty("审批备注")
    private String remarks;
    @ApiModelProperty("工单信息")
    private Ticket ticket;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
