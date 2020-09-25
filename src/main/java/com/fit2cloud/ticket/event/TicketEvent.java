package com.fit2cloud.ticket.event;

import com.fit2cloud.commons.server.process.ProcessEvent;
import com.fit2cloud.commons.server.process.ProcessEventContext;
import com.fit2cloud.commons.utils.CommonBeanFactory;
import com.fit2cloud.ticket.constants.TicketConstants;
import com.fit2cloud.ticket.service.TicketService;
import org.apache.commons.lang3.StringUtils;

public class TicketEvent implements ProcessEvent {

    @Override
    public void execute(ProcessEventContext context) {
        String para = context.getArguments();
        if (StringUtils.isBlank(para)) {
            return;
        }
        TicketConstants.Status status = TicketConstants.Status.valueOf(para);
        TicketService ticketService = CommonBeanFactory.getBean(TicketService.class);
        String id = context.getProcess().getBusinessKey();
        ticketService.updateTicketStatus(id, status.toString());
    }
}
