package com.fit2cloud.ticket.service;

import com.fit2cloud.commons.server.base.domain.FlowFormConfig;
import com.fit2cloud.commons.server.base.domain.FlowFormConfigExample;
import com.fit2cloud.commons.server.base.domain.FlowTask;
import com.fit2cloud.commons.server.base.mapper.FlowFormConfigMapper;
import com.fit2cloud.commons.server.base.mapper.FlowTaskMapper;
import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.commons.server.utils.SessionUtils;
import com.fit2cloud.ticket.controller.request.ListTicketFormRequest;
import com.fit2cloud.ticket.dao.mapper.TicketFormDeployMapper;
import com.fit2cloud.ticket.dao.mapper.TicketFormMapper;
import com.fit2cloud.ticket.dao.mapper.ext.ExtTicketMapper;
import com.fit2cloud.ticket.dao.model.TicketForm;
import com.fit2cloud.ticket.dao.model.TicketFormDeploy;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class FormDesignService {

    @Resource
    private TicketFormMapper ticketFormMapper;

    @Resource
    private TicketFormDeployMapper ticketFormDeployMapper;

    @Resource
    private ExtTicketMapper extTicketMapper;

    @Resource
    private FlowFormConfigMapper flowFormConfigMapper;

    @Resource
    private FlowTaskMapper flowTaskMapper;

    public List<TicketForm> listForm(ListTicketFormRequest request) {
        request.setName(StringUtils.wrapIfMissing(request.getName(), "%"));
        return extTicketMapper.listTicketForm(request);
    }

    /**
     * 获取表单
     * @param id 表单id
     * @return
     */
    public TicketForm getFormByFormId(String id) {
        return ticketFormMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取表单
     * @param id 流程id
     * @return
     */
    public TicketForm getFormByModelId(String id) {
        FlowFormConfigExample example = new FlowFormConfigExample();
        FlowFormConfigExample.Criteria criteria = example.createCriteria();
        criteria.andModelIdEqualTo(id);
        criteria.andStepEqualTo(0);//工单申请流程Step为0
        List<FlowFormConfig> flowFormConfig = flowFormConfigMapper.selectByExample(example);
        //TicketForm ticketForm = ticketFormMapper.selectByPrimaryKey(id);
        if (CollectionUtils.isEmpty(flowFormConfig)) {
            return null;
        }
        TicketForm ticketForm = ticketFormMapper.selectByPrimaryKey(flowFormConfig.get(0).getFormId());
        return ticketForm;
    }

    /**
     * 获取表单
     * @param id 流程ID
     * @param taskId 任务ID
     * @return step没配表单返回null
     */
    public TicketForm getFormByModelTaskId(String id,String taskId) {
        FlowTask flowTask = flowTaskMapper.selectByPrimaryKey(taskId);
        FlowFormConfigExample example = new FlowFormConfigExample();
        FlowFormConfigExample.Criteria criteria = example.createCriteria();
        criteria.andModelIdEqualTo(id);
        criteria.andStepEqualTo(flowTask.getTaskStep());
        List<FlowFormConfig> flowFormConfig = flowFormConfigMapper.selectByExample(example);
        if(flowFormConfig.size() > 0){
            TicketForm formCurrent = ticketFormMapper.selectByPrimaryKey(flowFormConfig.get(0).getFormId());
            return formCurrent;
        }else{
            return null;
        }
    }


    public int addForm(TicketForm form) {
        TicketForm exist = ticketFormMapper.selectByPrimaryKey(form.getId());
        if (exist != null) {
            F2CException.throwException("表单ID不可重复");
        }
        form.setCreator(SessionUtils.getUser().getEmail());
        form.setVersion(System.currentTimeMillis());
        return ticketFormMapper.insert(form);
    }

    public int updateForm(TicketForm form) {
        form.setCreator(SessionUtils.getUser().getEmail());
        form.setVersion(System.currentTimeMillis());
        form.setDeployId(null);
        return ticketFormMapper.updateByPrimaryKeyWithBLOBs(form);
    }

    public int copyForm(String oldId, String newId) {
        TicketForm exist = ticketFormMapper.selectByPrimaryKey(newId);
        if (exist != null) {
            F2CException.throwException("表单ID不可重复");
        }
        TicketForm form = ticketFormMapper.selectByPrimaryKey(oldId);
        form.setId(newId);
        form.setCreator(SessionUtils.getUser().getEmail());
        form.setVersion(System.currentTimeMillis());
        form.setDeployId(null);
        return ticketFormMapper.insert(form);
    }

    public int deleteForm(String id) {
        return ticketFormMapper.deleteByPrimaryKey(id);
    }

    public int publishForm(String id) {
        TicketForm model = ticketFormMapper.selectByPrimaryKey(id);
        if (StringUtils.isNotBlank(model.getDeployId())) {
            F2CException.throwException("模型已发布过");
        }
        TicketFormDeploy deploy = deployForm(model);
        model.setDeployId(deploy.getId());
        return ticketFormMapper.updateByPrimaryKeySelective(model);
    }

    private TicketFormDeploy deployForm(TicketForm form) {
        TicketFormDeploy deploy = new TicketFormDeploy();
        deploy.setId(UUID.randomUUID().toString());
        deploy.setContent(form.getContent());
        deploy.setTime(System.currentTimeMillis());
        deploy.setVersion(form.getVersion());
        deploy.setFormId(form.getId());
        ticketFormDeployMapper.insert(deploy);
        return deploy;
    }
}
