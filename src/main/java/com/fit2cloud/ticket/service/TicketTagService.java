package com.fit2cloud.ticket.service;

import com.fit2cloud.commons.server.exception.F2CException;
import com.fit2cloud.ticket.dao.mapper.TicketTagMapper;
import com.fit2cloud.ticket.dao.mapper.TicketTagMappingMapper;
import com.fit2cloud.ticket.dao.mapper.ext.ExtTicketMapper;
import com.fit2cloud.ticket.dao.model.TicketConfig;
import com.fit2cloud.ticket.dao.model.TicketTag;
import com.fit2cloud.ticket.dao.model.TicketTagMapping;
import com.fit2cloud.ticket.dao.model.TicketTagMappingExample;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.annotation.Resource;

@Service
@Transactional(rollbackFor = Exception.class)
public class TicketTagService {

    @Resource
    private TicketTagMapper ticketTagMapper;

    @Resource
    private TicketTagMappingMapper ticketTagMappingMapper;

    @Resource
    private ExtTicketMapper extTicketMapper;

    public List<TicketTag> list() {
        return ticketTagMapper.selectByExample(null);
    }

    public List<TicketTag> listUsingTag() {
        return extTicketMapper.listUsingTag();
    }

    public List<TicketTagMapping> listMapping() {
        return ticketTagMappingMapper.selectByExample(null);
    }

    public int save(TicketTag tag) {
        if (tag.getTagKey() == null) {
            tag.setTagKey(UUID.randomUUID().toString());
            return ticketTagMapper.insert(tag);
        } else {
            return ticketTagMapper.updateByPrimaryKey(tag);
        }
    }

    public int delete(String tagKey) {
        List<TicketConfig> list = extTicketMapper.listByTag(tagKey);
        if (list.size() > 0) {
            List<String> names = list.stream().map(TicketConfig::getName).collect(Collectors.toList());
            F2CException.throwException("标签已被工单模板" + names + "使用");
        }
        return ticketTagMapper.deleteByPrimaryKey(tagKey);
    }

    public void mapping(String configId, List<String> tagKeys) {
        if (tagKeys == null) return;
        deleteMapping(configId);
        tagKeys.forEach(key -> {
            TicketTagMapping tagMapping = new TicketTagMapping();
            tagMapping.setTagKey(key);
            tagMapping.setTicketConfigId(configId);
            ticketTagMappingMapper.insert(tagMapping);
        });
    }

    public List<String> listTagMapping(String configId) {
        TicketTagMappingExample example = new TicketTagMappingExample();
        example.createCriteria().andTicketConfigIdEqualTo(configId);
        List<TicketTagMapping> list = ticketTagMappingMapper.selectByExample(example);
        return list.stream().map(TicketTagMapping::getTagKey).collect(Collectors.toList());
    }

    public void deleteMapping(String configId) {
        TicketTagMappingExample example = new TicketTagMappingExample();
        example.createCriteria().andTicketConfigIdEqualTo(configId);
        ticketTagMappingMapper.deleteByExample(example);
    }
}
