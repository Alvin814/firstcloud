package com.fit2cloud.ticket.dao.mapper.ext;

import com.fit2cloud.ticket.controller.request.ListTicketConfigRequest;
import com.fit2cloud.ticket.controller.request.ListTicketFormRequest;
import com.fit2cloud.ticket.controller.request.ListTicketRequest;
import com.fit2cloud.ticket.dao.model.Ticket;
import com.fit2cloud.ticket.dao.model.TicketConfig;
import com.fit2cloud.ticket.dao.model.TicketForm;
import com.fit2cloud.ticket.dao.model.TicketTag;
import com.fit2cloud.ticket.dao.model.ext.TicketCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExtTicketMapper {

    List<TicketForm> listTicketForm(ListTicketFormRequest request);

    List<Ticket> listTicket(ListTicketRequest request);

    List<TicketConfig> listTicketConfig(ListTicketConfigRequest request);

    List<TicketConfig> listByTag(String tagKey);

    List<TicketTag> listUsingTag();

    TicketCount analysisAllTicket();

    TicketCount analysisMyTicket(@Param("creator") String creator, @Param("workspaceId") String workspaceId);
}