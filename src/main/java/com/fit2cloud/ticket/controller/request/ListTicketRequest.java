package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ListTicketRequest {

    @ApiModelProperty(value = "模糊查询: 工单名称或发起人ID", hidden = true)
    private String quickSearch;

    @ApiModelProperty(value = "模糊查询: 工单名称", hidden = true)
    private String name;

    @ApiModelProperty(value = "模糊查询: 发起人ID", hidden = true)
    private String creator;

    @ApiModelProperty("发起人ID")
    private String owner;

    @ApiModelProperty(value = "工单状态", allowableValues = "RUNNING, COMPLETE, TERMINATED, CANCEL")
    private String status;

    @ApiModelProperty("处理方案")
    private String handleProgram;

    @ApiModelProperty("处理结果")
    private String handleResult;

    @ApiModelProperty("处理结果确认")
    private String ticketHandleResult;

    @ApiModelProperty("处理速度")
    private Integer handleSpeed;

    @ApiModelProperty("处理态度")
    private Integer handleAttitude;

    @ApiModelProperty("处理满意度")
    private Integer handleSatisfaction;

    @ApiModelProperty("处理时长")
    private Integer handleTime;

    @ApiModelProperty("工作空间ID")
    private String workspaceId;

    @ApiModelProperty(value = "工作空间列表", hidden = true)
    private List<String> workspaceIds;

    @ApiModelProperty("创建时间: 开始时间戳(包含)")
    private Long timeStart;

    @ApiModelProperty("创建时间: 结束时间戳(不包含)")
    private Long timeEnd;

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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(String workspaceId) {
        this.workspaceId = workspaceId;
    }

    public List<String> getWorkspaceIds() {
        return workspaceIds;
    }

    public void setWorkspaceIds(List<String> workspaceIds) {
        this.workspaceIds = workspaceIds;
    }

    public Long getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(Long timeStart) {
        this.timeStart = timeStart;
    }

    public Long getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(Long timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getHandleProgram() {
        return handleProgram;
    }

    public void setHandleProgram(String handleProgram) {
        this.handleProgram = handleProgram;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public String getTicketHandleResult() {
        return ticketHandleResult;
    }

    public void setTicketHandleResult(String ticketHandleResult) {
        this.ticketHandleResult = ticketHandleResult;
    }

    public Integer getHandleSpeed() {
        return handleSpeed;
    }

    public void setHandleSpeed(Integer handleSpeed) {
        this.handleSpeed = handleSpeed;
    }

    public Integer getHandleAttitude() {
        return handleAttitude;
    }

    public void setHandleAttitude(Integer handleAttitude) {
        this.handleAttitude = handleAttitude;
    }

    public Integer getHandleSatisfaction() {
        return handleSatisfaction;
    }

    public void setHandleSatisfaction(Integer handleSatisfaction) {
        this.handleSatisfaction = handleSatisfaction;
    }

    public Integer getHandleTime() { return handleTime; }

    public void setHandleTime(Integer handleTime) { this.handleTime = handleTime; }
}
