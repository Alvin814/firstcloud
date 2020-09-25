package com.fit2cloud.ticket.dao.model.ext;

import com.fit2cloud.ticket.dao.model.Ticket;
import io.swagger.annotations.ApiModelProperty;

public class ExtTicket extends Ticket {

    @ApiModelProperty("标题")
    private String title;

    @ApiModelProperty("紧急程度")
    private String priority;

    @ApiModelProperty("联系人")
    private String linkman;

    @ApiModelProperty("联系电话")
    private String phone;

    @ApiModelProperty("处理期限")
    private String endTime;

    @ApiModelProperty("工单处理确认")
    private String handleResult;

    @ApiModelProperty("处理速度评价(分)")
    private String speed;

    @ApiModelProperty("处理态度评价(分)")
    private String handleAttitude;

    @ApiModelProperty("处理满意度评价(分)")
    private String handleSatisfaction;

    @ApiModelProperty("页面处理时长")
    private String viewHandleTime;


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getLinkman() {
        return linkman;
    }

    public void setLinkman(String linkman) {
        this.linkman = linkman;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getHandleResult() { return handleResult; }

    public void setHandleResult(String handleResult) { this.handleResult = handleResult; }

    public String getSpeed() { return speed; }

    public void setSpeed(String speed) { this.speed = speed; }

    public String getHandleAttitude() { return handleAttitude; }

    public void setHandleAttitude(String handleAttitude) { this.handleAttitude = handleAttitude; }

    public String getHandleSatisfaction() { return handleSatisfaction; }

    public void setHandleSatisfaction(String handleSatisfaction) { this.handleSatisfaction = handleSatisfaction; }

    public String getViewHandleTime() { return viewHandleTime; }

    public void setViewHandleTime(String viewHandleTime) { this.viewHandleTime = viewHandleTime; }
}
