package com.fit2cloud.ticket.controller;

import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.ticket.constants.TicketPermissionConstants;
import com.fit2cloud.ticket.controller.request.ListTicketConfigRequest;
import com.fit2cloud.ticket.dao.model.TicketConfig;
import com.fit2cloud.ticket.dao.model.ext.ExtTicketConfig;
import com.fit2cloud.ticket.service.TicketConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

@Api(tags = "工单模板")
@RestController
@RequestMapping(value = "/ticket/config")
public class TicketConfigController {

    @Resource
    private TicketConfigService ticketConfigService;

    @ApiOperation(value = "获取工单模板列表")
    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @PostMapping(value = "/list/{goPage}/{pageSize}")
    public Pager<List<TicketConfig>> list(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ListTicketConfigRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, ticketConfigService.list(request));
    }

    @RequiresPermissions(value = {TicketPermissionConstants.TICKET_CONFIG_MANAGER, TicketPermissionConstants.TICKET_APPLY}, logical = Logical.OR)
    @PostMapping(value = "/list")
    public List<TicketConfig> list(@RequestBody ListTicketConfigRequest request) {
        return ticketConfigService.list(request);
    }

    @ApiOperation(value = "获取工单模板")
    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @GetMapping(value = "/get/{id}")
    public ExtTicketConfig getTicketConfigById(@PathVariable String id) {
        return ticketConfigService.getTicketConfigById(id);
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @PostMapping(value = "/add")
    public int add(@RequestBody ExtTicketConfig config) {
        return ticketConfigService.add(config);
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @PostMapping(value = "/update")
    public int update(@RequestBody ExtTicketConfig config) {
        return ticketConfigService.update(config);
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @GetMapping(value = "/delete/{id}")
    public int delete(@PathVariable String id) {
        return ticketConfigService.delete(id);
    }

}
