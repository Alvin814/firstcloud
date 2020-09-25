package com.fit2cloud.ticket.dao.model;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class GHCost {

    @ApiModelProperty("ID")
    private String id;

    @ApiModelProperty("工单ID")
    private String ticketId;

    @ApiModelProperty("填写人ID")
    private String userId;

    @ApiModelProperty("工作空间ID")
    private String workspaceId;

    @ApiModelProperty("组织ID")
    private String organizationId;

    @ApiModelProperty("工时")
    private BigDecimal cost = BigDecimal.ZERO;

    @ApiModelProperty("年")
    private String syncYear;

    @ApiModelProperty("周")
    private String syncMonth;

    @ApiModelProperty("周")
    private String syncWeek;

    @ApiModelProperty("日")
    private String syncDay;

    @ApiModelProperty("时")
    private String syncHour;

    @ApiModelProperty("创建时间")
    private Long time;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public BigDecimal getCost() {
        return cost;
    }

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSyncYear() {
        return syncYear;
    }

    public void setSyncYear(String syncYear) {
        this.syncYear = syncYear;
    }

    public String getSyncMonth() {
        return syncMonth;
    }

    public void setSyncMonth(String syncMonth) {
        this.syncMonth = syncMonth;
    }

    public String getSyncWeek() {
        return syncWeek;
    }

    public void setSyncWeek(String syncWeek) {
        this.syncWeek = syncWeek;
    }

    public String getSyncDay() {
        return syncDay;
    }

    public void setSyncDay(String syncDay) {
        this.syncDay = syncDay;
    }

    public String getSyncHour() {
        return syncHour;
    }

    public void setSyncHour(String syncHour) {
        this.syncHour = syncHour;
    }
}
