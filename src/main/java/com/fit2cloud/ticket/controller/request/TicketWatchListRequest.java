package com.fit2cloud.ticket.controller.request;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

public class TicketWatchListRequest {


    @ApiModelProperty("值班人名称")
    private String userName;

    @ApiModelProperty("值班日期")
    private Date watchDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getWatchDate() {
        return watchDate;
    }

    public void setWatchDate(Date watchDate) {
        this.watchDate = watchDate;
    }
}