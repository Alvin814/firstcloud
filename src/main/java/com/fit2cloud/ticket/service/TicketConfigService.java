package com.fit2cloud.ticket.service;

import com.fit2cloud.commons.utils.BeanUtils;
import com.fit2cloud.ticket.controller.request.ListTicketConfigRequest;
import com.fit2cloud.ticket.dao.mapper.TicketConfigMapper;
import com.fit2cloud.ticket.dao.mapper.ext.ExtTicketMapper;
import com.fit2cloud.ticket.dao.model.TicketConfig;
import com.fit2cloud.ticket.dao.model.ext.ExtTicketConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketConfigService {

    @Resource
    private TicketTagService ticketTagService;

    @Resource
    private TicketConfigMapper ticketConfigMapper;

    @Resource
    private ExtTicketMapper extTicketMapper;

    public List<TicketConfig> list(ListTicketConfigRequest request) {
        request.setName(StringUtils.wrapIfMissing(request.getName(), "%"));
        request.setDescription(StringUtils.wrapIfMissing(request.getDescription(), "%"));
        return extTicketMapper.listTicketConfig(request);
    }

    public ExtTicketConfig getTicketConfigById(String id) {
        ExtTicketConfig config = new ExtTicketConfig();
        BeanUtils.copyBean(config, ticketConfigMapper.selectByPrimaryKey(id));
        config.setTags(ticketTagService.listTagMapping(config.getId()));
        return config;
    }

    public int add(ExtTicketConfig config) {
        config.setId(UUID.randomUUID().toString());
        config.setTime(System.currentTimeMillis());
        ticketTagService.mapping(config.getId(), config.getTags());
        return ticketConfigMapper.insert(config);
    }

    public int update(ExtTicketConfig config) {
        config.setTime(System.currentTimeMillis());
        ticketTagService.mapping(config.getId(), config.getTags());
        return ticketConfigMapper.updateByPrimaryKey(config);
    }

    public int delete(String id) {
        ticketTagService.deleteMapping(id);
        return ticketConfigMapper.deleteByPrimaryKey(id);
    }
}
