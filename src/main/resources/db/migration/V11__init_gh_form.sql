INSERT INTO ticket_form (id,
                         name,
                         content,
                         version,
                         creator,
                         deploy_id)
VALUES
                        ('GH',
                         '【交付参数】交付工时输入',
                         '{
                                "event": {
                                    "names": [
                                        "onInit"
                                    ]
                                },
                                "rows": [
                                    {
                                        "cells": [
                                            {
                                                "width": "auto",
                                                "element": {
                                                    "id": "16e0d7b7-7d17-494a-ae3f-83346737fe0e",
                                                    "property": {
                                                        "id": "16e0d7b7-7d17-494a-ae3f-83346737fe0e",
                                                        "type": "input",
                                                        "label": "工单处理耗时（单位：小时）",
                                                        "inputType": "text",
                                                        "max": 5,
                                                        "hint": "请输入处理工单的实际工作量，以工时为单位",
                                                        "value": null,
                                                        "required": true
                                                    },
                                                    "event": {
                                                        "names": [
                                                            "onChange",
                                                            "onBlur"
                                                        ]
                                                    }
                                                }
                                            }
                                        ]
                                    },
                                    {
                                        "cells": [
                                            {
                                                "width": "auto",
                                                "element": {
                                                    "id": "8e6ff478-3f5a-4859-810f-4bdea1f41d7a",
                                                    "property": {
                                                        "id": "8e6ff478-3f5a-4859-810f-4bdea1f41d7a",
                                                        "type": "textarea",
                                                        "label": "工单处理说明",
                                                        "rows": 3,
                                                        "hint": "请输入工单处理过程中的工作内容说明"
                                                    },
                                                    "event": {
                                                        "names": [
                                                            "onChange",
                                                            "onBlur"
                                                        ]
                                                    }
                                                }
                                            }
                                        ]
                                    }
                                ]
                            }
                                     ',
                         1541581179523,
                         'admin@fit2cloud.com',
                         null);
