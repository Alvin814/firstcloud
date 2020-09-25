INSERT INTO flow_model (model_id, model_name, model_content, model_version, model_creator, model_time, deploy_id, module) VALUES ('TICKET_EXAMPLE', '工单示例流程', '{
    "activities": [
        {
            "step": 0,
            "activityId": "c915504c-a848-4f57-9f08-f1ed194ad7e1",
            "name": "提交申请",
            "url": "",
            "assigneeType": "CREATOR",
            "assignee": "OWNER"
        },
        {
            "activityId": "e7349659-ced5-45e4-b756-979661931200",
            "step": 1,
            "name": "组织管理员审批",
            "assigneeType": "SYSTEM_ROLE",
            "assigneeValue": "ORGADMIN",
            "jump": true,
            "assignee": "ORGADMIN"
        },
        {
            "activityId": "098d9e3e-bf16-4ca2-aa78-a4b070d38286",
            "step": 2,
            "name": "系统管理员处理",
            "assigneeType": "SYSTEM_ROLE",
            "assigneeValue": "ADMIN",
            "assignee": "ADMIN"
        }
    ]
}', 1541580811349, 'admin@fit2cloud.com', '2018-11-07 16:53:31', 'a8d2054f-5221-491d-8f8b-9cdbc88b657d', 'ticket-center');

INSERT INTO flow_deploy (deploy_id, deploy_content, deploy_version, deploy_time, model_id) VALUES ('a8d2054f-5221-491d-8f8b-9cdbc88b657d', '{
    "activities": [
        {
            "step": 0,
            "activityId": "c915504c-a848-4f57-9f08-f1ed194ad7e1",
            "name": "提交申请",
            "url": "",
            "assigneeType": "CREATOR",
            "assignee": "OWNER"
        },
        {
            "activityId": "e7349659-ced5-45e4-b756-979661931200",
            "step": 1,
            "name": "组织管理员审批",
            "assigneeType": "SYSTEM_ROLE",
            "assigneeValue": "ORGADMIN",
            "jump": true,
            "assignee": "ORGADMIN"
        },
        {
            "activityId": "098d9e3e-bf16-4ca2-aa78-a4b070d38286",
            "step": 2,
            "name": "系统管理员处理",
            "assigneeType": "SYSTEM_ROLE",
            "assigneeValue": "ADMIN",
            "assignee": "ADMIN"
        }
    ]
}', 1541580811349, 1541580813978, 'TICKET_EXAMPLE');

INSERT INTO flow_event (name, executor, model_id, step, type, position, operation, arguments, module) VALUES ('流程完成', 'com.fit2cloud.ticket.event.TicketEvent', 'TICKET_EXAMPLE', -1, 'PROCESS', null, 'COMPLETE', 'COMPLETE', 'ticket-center');
INSERT INTO flow_event (name, executor, model_id, step, type, position, operation, arguments, module) VALUES ('流程中止', 'com.fit2cloud.ticket.event.TicketEvent', 'TICKET_EXAMPLE', -1, 'PROCESS', null, 'TERMINATE', 'TERMINATED', 'ticket-center');
INSERT INTO flow_event (name, executor, model_id, step, type, position, operation, arguments, module) VALUES ('流程撤销', 'com.fit2cloud.ticket.event.TicketEvent', 'TICKET_EXAMPLE', -1, 'PROCESS', null, 'CANCEL', 'CANCEL', 'ticket-center');

INSERT INTO flow_notification_config (model_id, step, process_type, sms_type, position, operation, template, module) VALUES ('TICKET_EXAMPLE', -1, 'PROCESS', 'ANNOUNCEMENT,EMAIL', null, 'COMPLETE', '{
    "receivers": [
        {
            "key": "CREATOR",
            "name": "申请人",
            "checked": true
        },
        {
            "key": "ASSIGNEE",
            "name": "环节处理人"
        },
        {
            "key": "OTHER",
            "name": "其他"
        }
    ],
    "title": "工单处理完成",
    "content": "您的工单已处理完成。",
    "others": []
}', 'ticket-center');
INSERT INTO flow_notification_config (model_id, step, process_type, sms_type, position, operation, template, module) VALUES ('TICKET_EXAMPLE', -1, 'PROCESS', 'ANNOUNCEMENT,EMAIL', null, 'TERMINATE', '{
    "receivers": [
        {
            "key": "CREATOR",
            "name": "申请人",
            "checked": true
        },
        {
            "key": "ASSIGNEE",
            "name": "环节处理人"
        },
        {
            "key": "OTHER",
            "name": "其他"
        }
    ],
    "title": "工单已被驳回",
    "content": "您提交的工单已被驳回。",
    "others": []
}', 'ticket-center');

INSERT INTO ticket_form (id, name, content, version, creator, deploy_id) VALUES ('FORM_EXAMPLE', '工单示例表单', '{"event":{"names":["onInit"]},"rows":[{"cells":[{"width":"50","element":{"id":"3d5ff9a3-eea8-41c9-b70f-5b4812c7e278","property":{"id":"name","type":"input","label":"姓名","inputType":"text","max":20,"required":true},"event":{"names":["onChange","onBlur"]}}},{"width":"50","element":{"id":"37c58743-3793-4a52-ab78-63e9ab661bd1","property":{"id":"age","type":"input","label":"年龄","inputType":"number","max":10,"hint":"年龄小于20则出现提示框","required":true},"event":{"names":["onChange","onBlur"],"onChange":"if(self.value < 20) service.notice.info(\\"年龄小于20\\");"}}}]},{"cells":[{"width":"auto","element":{"id":"1914302d-c249-44a0-b047-e43046222e80","property":{"id":"city","type":"select","label":"城市","options":[{"key":"bj","value":"北京"},{"key":"sh","value":"上海"},{"key":"sz","value":"深圳"}],"required":true},"event":{"names":["onChange"]}}}]}]}', 1541581179523, 'admin@fit2cloud.com', 'fef212ba-04af-4d13-a72e-fe5400898172');

INSERT INTO ticket_form_deploy (id, form_id, content, version, time) VALUES ('fef212ba-04af-4d13-a72e-fe5400898172', 'FORM_EXAMPLE', '{"event":{"names":["onInit"]},"rows":[{"cells":[{"width":"50","element":{"id":"3d5ff9a3-eea8-41c9-b70f-5b4812c7e278","property":{"id":"name","type":"input","label":"姓名","inputType":"text","max":20,"required":true},"event":{"names":["onChange","onBlur"]}}},{"width":"50","element":{"id":"37c58743-3793-4a52-ab78-63e9ab661bd1","property":{"id":"age","type":"input","label":"年龄","inputType":"number","max":10,"hint":"年龄小于20则出现提示框","required":true},"event":{"names":["onChange","onBlur"],"onChange":"if(self.value < 20) service.notice.info(\\"年龄小于20\\");"}}}]},{"cells":[{"width":"auto","element":{"id":"1914302d-c249-44a0-b047-e43046222e80","property":{"id":"city","type":"select","label":"城市","options":[{"key":"bj","value":"北京"},{"key":"sh","value":"上海"},{"key":"sz","value":"深圳"}],"required":true},"event":{"names":["onChange"]}}}]}]}', 1541581179523, 1541581180532);

INSERT INTO ticket_config (id, name, description, form_id, model_id, time) VALUES ('14bcbece-7c8f-40ef-a252-6b6236c3614b', '工单模板示例', '工单模板示例', 'FORM_EXAMPLE', 'TICKET_EXAMPLE', 1541581224723);
