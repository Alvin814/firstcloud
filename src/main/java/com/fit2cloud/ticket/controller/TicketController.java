package com.fit2cloud.ticket.controller;

import com.fit2cloud.commons.server.base.domain.User;
import com.fit2cloud.commons.server.utils.IDGenerator;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.commons.utils.ResultHolder;
import com.fit2cloud.ticket.constants.TicketConstants;
import com.fit2cloud.ticket.constants.TicketPermissionConstants;
import com.fit2cloud.ticket.controller.request.*;
import com.fit2cloud.ticket.dao.model.Ticket;
import com.fit2cloud.ticket.dao.model.TicketWatchList;
import com.fit2cloud.ticket.dao.model.ext.ExtTicket;
import com.fit2cloud.ticket.dao.model.ext.TicketCount;
import com.fit2cloud.ticket.service.TicketService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

@Api(tags = "工单")
@RestController
@RequestMapping(value = "/ticket")
public class TicketController {

    @Resource
    private TicketService ticketService;

    @ApiOperation("工单列表")
    @RequiresPermissions(TicketPermissionConstants.TICKET_READ)
    @PostMapping(value = "/list/{goPage}/{pageSize}")
    public Pager<List<ExtTicket>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ListTicketRequest request) {
        ticketService.addRoleParameters(request);
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, ticketService.list(request));
    }

    @ApiOperation("删除工单(工单ID)")
    @RequiresPermissions(TicketPermissionConstants.TICKET_DELETE)
    @GetMapping(value = "/delete/{id}")
    public int delete(@PathVariable String id) {
        return ticketService.delete(id);
    }

    @ApiOperation("获取工单(工单ID)")
    @RequiresPermissions({TicketPermissionConstants.TICKET_READ})
    @GetMapping(value = "/get/{id}")
    public Ticket getTicketById(@PathVariable String id) {
        return ticketService.getTicketById(id);
    }

    @RequiresPermissions({TicketPermissionConstants.TICKET_READ})
    @GetMapping(value = "/count/all")
    public TicketCount analysisAllTicket() {
        return ticketService.analysisAllTicket();
    }

    @ApiOperation("创建工单")
    @RequiresPermissions({TicketPermissionConstants.TICKET_APPLY})
    @PostMapping(value = "/create")
    public Ticket create(@RequestBody CreateTicketRequest request) {
        return ticketService.create(request);
    }

    @ApiOperation("撤销工单")
    @PostMapping(value = "/cancel")
    public void cancel(@RequestBody CancelTicketRequest cancelTicketRequest) {
        ticketService.cancel(cancelTicketRequest);
    }

    @RequiresPermissions({TicketPermissionConstants.TICKET_READ})
    @GetMapping(value = "/count/own")
    public TicketCount analysisMyTicket() {
        return ticketService.analysisMyTicket();
    }

    @ApiOperation("获取工单(任务ID)")
    @GetMapping(value = "/approve/get/{taskId}")
    public Ticket getTicketByTaskId(@PathVariable String taskId) {
        return ticketService.getTicketByTaskId(taskId);
    }

    @ApiOperation("审批工单-通过")
    @PostMapping(value = "/complete")
    public void complete(@RequestBody ApproveTicketRequest approveTicketRequest) {
        ticketService.complete(approveTicketRequest);
    }

    @ApiOperation("审批工单-驳回")
    @PostMapping(value = "/reject")
    public void reject(@RequestBody ApproveTicketRequest approveTicketRequest) {
        ticketService.reject(approveTicketRequest);
    }

    @ApiOperation("生成工单ID")
    @GetMapping(value = "/uuid")
    public String createTicketID() {
        return IDGenerator.newBusinessId(TicketConstants.TICKET_ID_PREFIX, SessionUtils.getUser().getSourceId());
    }

    @ApiOperation("导出工单")
    @RequestMapping(value = "/export")
    public Object exportTickets(HttpServletResponse response,ListTicketRequest request){
        try {
            ticketService.exportTicket(response,request);
            return new ResultHolder();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation("工单值班人列表")
    @PostMapping(value = "/watch/list/{goPage}/{pageSize}")
    public Pager<List<TicketWatchList>> getWatchLists(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody TicketWatchListRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, ticketService.getWatchLists(request));
    }

    @ApiOperation("创建值班表")
    @RequestMapping(value = "/watch/list/create")
    public Object createWatchList(@RequestBody TicketWatchList record) {
        try {
            ticketService.createWatchList(record);
            return new ResultHolder();
        } catch (Exception e) {
            return e.getMessage();
        }
    }

    @ApiOperation("删除值班表")
    @RequestMapping(value = "/watch/list/delete/{id}")
    public void deleteWatchList(@PathVariable int id) {
            ticketService.deleteWatchList(id);
    }

    @ApiOperation("更新值班表")
    @PostMapping(value = "/watch/list/update")
    public void updateWatchList(@RequestBody TicketWatchList record) {
            ticketService.updateWatchList(record);
    }

    @ApiOperation("获取所有值班人信息")
    @RequestMapping(value = "/watch/dayUsers")
    public List<User> getDayUsers() {
            return ticketService.getDayusers();
    }

}
