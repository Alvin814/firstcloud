package com.fit2cloud.ticket.controller;

import com.fit2cloud.commons.utils.ResultHolder;
import com.fit2cloud.ticket.constants.TicketPermissionConstants;
import com.fit2cloud.ticket.dao.model.TicketTag;
import com.fit2cloud.ticket.dao.model.TicketTagMapping;
import com.fit2cloud.ticket.service.TicketTagService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/ticket/tag")
public class TicketTagController {

    @Resource
    private TicketTagService ticketTagService;

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @GetMapping(value = "/list")
    public List<TicketTag> list() {
        return ticketTagService.list();
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_APPLY)
    @GetMapping(value = "/list/using")
    public List<TicketTag> listUsingTag() {
        return ticketTagService.listUsingTag();
    }

    @RequiresPermissions(value = {TicketPermissionConstants.TICKET_CONFIG_MANAGER, TicketPermissionConstants.TICKET_APPLY}, logical = Logical.OR)
    @GetMapping(value = "/mapping/list")
    public List<TicketTagMapping> listMapping() {
        return ticketTagService.listMapping();
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @PostMapping(value = "/save")
    public int add(@RequestBody TicketTag tag) {
        return ticketTagService.save(tag);
    }

    @RequiresPermissions(TicketPermissionConstants.TICKET_CONFIG_MANAGER)
    @GetMapping(value = "/delete/{tagKey}")
    public ResultHolder delete(@PathVariable String tagKey) {
        try {
            return ResultHolder.success(ticketTagService.delete(tagKey));
        } catch (RuntimeException e) {
            return ResultHolder.error(e.getMessage());
        }
    }

}
