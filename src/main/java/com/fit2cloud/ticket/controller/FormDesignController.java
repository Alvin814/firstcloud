package com.fit2cloud.ticket.controller;

import com.fit2cloud.commons.utils.PageUtils;
import com.fit2cloud.commons.utils.Pager;
import com.fit2cloud.ticket.constants.TicketPermissionConstants;
import com.fit2cloud.ticket.controller.request.ListTicketFormRequest;
import com.fit2cloud.ticket.dao.model.TicketForm;
import com.fit2cloud.ticket.service.FormDesignService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/form/design")
public class FormDesignController {

    @Resource
    private FormDesignService formDesignService;

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @PostMapping(value = "/template/list/{goPage}/{pageSize}")
    public Pager<List<TicketForm>> listForm(@PathVariable int goPage, @PathVariable int pageSize, @RequestBody ListTicketFormRequest request) {
        Page page = PageHelper.startPage(goPage, pageSize, true);
        return PageUtils.setPageInfo(page, formDesignService.listForm(request));
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @PostMapping(value = "/template/list")
    public List<TicketForm> listForm(@RequestBody ListTicketFormRequest request) {
        return formDesignService.listForm(request);
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @PostMapping(value = "/template/add")
    public int addForm(@RequestBody TicketForm model) {
        return formDesignService.addForm(model);
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @GetMapping(value = "/template/copy/{oldId}/{newId}")
    public int copyForm(@PathVariable String oldId, @PathVariable String newId) {
        return formDesignService.copyForm(oldId, newId);
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @PostMapping(value = "/template/update")
    public int updateForm(@RequestBody TicketForm model) {
        return formDesignService.updateForm(model);
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @GetMapping(value = "/template/delete/{id}")
    public int deleteForm(@PathVariable String id) {
        return formDesignService.deleteForm(id);
    }

    @RequiresPermissions(TicketPermissionConstants.FORM_MANAGER)
    @GetMapping(value = "/template/publish/{id}")
    public int publishForm(@PathVariable String id) {
        return formDesignService.publishForm(id);
    }

    @GetMapping(value = "/template/get/{id}")
    public TicketForm getFormByFormId(@PathVariable String id) {
        return formDesignService.getFormByFormId(id);
    }

    @GetMapping(value = "/template/get/byModel/{id}")
    public TicketForm getFormByModelId(@PathVariable String id) {
        return formDesignService.getFormByModelId(id);
    }

    @GetMapping(value = "/template/get/byModel/{id}/{taskId}")
    public TicketForm getFormByModelTaskId(@PathVariable String id,@PathVariable String taskId) {
        return formDesignService.getFormByModelTaskId(id,taskId);
    }

}
