let TicketApp = angular.module('TicketApp', ['f2c.form.common', 'f2c.process']);

TicketApp.filter('props', function () {
    return function (items, props) {
        let out = [];

        if (angular.isArray(items)) {
            let keys = Object.keys(props);

            items.forEach(function (item) {
                let itemMatches = false;

                for (let i = 0; i < keys.length; i++) {
                    let prop = keys[i];
                    let text = props[prop].toLowerCase();
                    if (("" + item[prop]).toLowerCase().indexOf(text) !== -1) {
                        itemMatches = true;
                        break;
                    }
                }

                if (itemMatches) {
                    out.push(item);
                }
            });
        } else {
            out = items;
        }

        return out;
    };
});

TicketApp.controller('TicketConfigListCtrl', function ($scope, $element, $document, $mdDialog, HttpUtils, Notification, FilterSearch, FormService, ModelManagement, $filter) {
    const translator = $filter('translator');
    $scope.init = function () {
        $scope.conditions = [
            {key: "name", name: translator('i18n_ticket_template_name', '工单模板名称'), directive: "filter-contains"},
            {key: "description", name: translator('i18n_ticket_description', '工单模板描述'), directive: "filter-contains"}
        ];
        $scope.filters = [];

        $scope.columns = [
            {value: translator('i18n_ticket_template_name', '工单模板名称'), key: "name", width: '15%'},
            {value: translator('i18n_ticket_template_description', '工单模板描述'), key: "description", width: '30%'},
            {value: translator('i18n_form_name', '表单名称'), key: "form_id", width: '15%', checked: false},
            {value: translator('i18n_process_name', '流程名称'), key: "model_id", width: '15%'},
            {value: translator('i18n_ticket_update_date', '修改时间'), key: "time", width: '15%'},
            {value: "", sort: false, default: true, width: '10%'}
        ];

        $scope.listModel();
        $scope.listForm();
        $scope.list();
    };

    $scope.delete = function (item) {
        Notification.confirm(translator('i18n_ticket_delete_confirm', '确认执行删除?'), function () {
            $scope.loadingLayer = HttpUtils.get('ticket/config/delete/' + item.id, function () {
                $scope.list();
                Notification.success(translator('i18n_ticket_delete_success', '删除成功'));
            }, function (response) {
                Notification.danger(translator('i18n_ticket_delete_failed', '删除失败') + "，" + response.message);
            });
        });
    };

    $scope.edit = function (item) {
        $scope.listTag();
        if (!item.id) {
            $scope.item = item;
        } else {
            $scope.item = null;
            $scope.sidenavLoading = HttpUtils.get('ticket/config/get/' + item.id, function (response) {
                $scope.item = response.data;
            });
        }

        $scope.formUrl = 'project/html/ticket/ticket-config-edit.html' + '?_t=' + window.appversion;
        $scope.toggleForm(function () {
            let input = $element.find('.select-search');
            if (input.length > 0) {
                input.on('keydown', function (ev) {
                    ev.stopPropagation();
                });
            }
        });
    };

    $scope.save = function () {
        let type = "add";
        if ($scope.item.id) {
            type = 'update';
        }
        $scope.sidenavLoading = HttpUtils.post('ticket/config/' + type, $scope.item, function () {
            $scope.list();
            Notification.success(translator('i18n_ticket_save_success', '保存成功'));
            $scope.close();
        }, function (response) {
            Notification.danger(translator('i18n_ticket_save_failed', '保存失败') + "，" + response.message);
        });
    };

    $scope.tag = function () {
        $scope.formUrl = 'project/html/ticket/ticket-tag.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.close = function () {
        $scope.toggleForm();
    };

    $scope.editModel = function (item) {
        ModelManagement.edit(item.modelId);
    };

    $scope.editForm = function (item) {
        FormService.edit(item.formId);
    };

    $scope.listModel = function () {
        $scope.loadingLayer = HttpUtils.get('flow/design/model/list', function (response) {
            $scope.models = [];
            angular.forEach(response.data, function (model) {
                let obj = {key: model.modelId, name: model.modelName};
                if (!model.deployId) {
                    obj.name += translator('i18n_not_published', '[未发布]');
                }
                $scope.models.push(obj);
            });
        });
    };

    $scope.listForm = function () {
        $scope.loadingLayer = HttpUtils.post("form/design/template/list", {}, function (response) {
            $scope.forms = [];
            angular.forEach(response.data, function (form) {
                let obj = {key: form.id, name: form.name};
                if (!form.deployId) {
                    obj.name += translator('i18n_not_published', '[未发布]');
                }
                $scope.forms.push(obj);
            });
        });
    };

    $scope.listTag = function () {
        HttpUtils.get("ticket/tag/list", function (response) {
            $scope.tags = response.data;
        });
    };

    $scope.list = function (sortObj) {
        let condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "ticket/config/list", condition);
    };

    $scope.init();
});

TicketApp.controller('TicketTagCtrl', function ($scope, HttpUtils, Notification, $filter) {
    const translator = $filter('translator');
    $scope.list = function () {
        $scope.sidenavLoading = HttpUtils.get("ticket/tag/list", function (response) {
            $scope.items = response.data;
            $scope.add();
        })
    };

    $scope.add = function () {
        $scope.items.push({edit: true, index: 1});
    };

    $scope.save = function (item) {
        item.tagName = item.temp;
        $scope.sidenavLoading = HttpUtils.post('ticket/tag/save/', item, function () {
            $scope.list();
            Notification.success(translator('i18n_ticket_save_success', '保存成功'));
        }, function (response) {
            Notification.danger(translator('i18n_ticket_save_failed', '保存失败') + "，" + response.message);
        });
    };

    $scope.delete = function (item) {
        Notification.confirm(translator('i18n_ticket_delete_confirm', '确认执行删除?'), function () {
            $scope.sidenavLoading = HttpUtils.get('ticket/tag/delete/' + item.tagKey, function () {
                $scope.list();
                Notification.success(translator('i18n_ticket_delete_success', '删除成功'));
            }, function (response) {
                Notification.danger(translator('i18n_ticket_delete_failed', '删除失败') + "，" + response.message);
            });
        });
    };

    $scope.close = function () {
        $scope.$parent.$parent.close();
    };

    $scope.list();

});

TicketApp.controller('TicketApplyCtrl', function ($scope, HttpUtils, Loading, Notification, $filter) {
    const translator = $filter('translator');
    $scope.param = "";
    $scope.all = true;

    $scope.listConfig = function () {
        Loading.add(HttpUtils.post("ticket/config/list", {}, function (response) {
            $scope.configs = response.data;
        }));
    };

    $scope.listTag = function () {
        Loading.add(HttpUtils.get("ticket/tag/list/using", function (response) {
            $scope.tags = response.data;
            $scope.tagMap = {};
            angular.forEach($scope.tags, function (tag) {
                $scope.tagMap[tag.tagKey] = tag;
            })
        }));
    };

    $scope.listMapping = function () {
        Loading.add(HttpUtils.get("ticket/tag/mapping/list", function (response) {
            $scope.mappings = response.data;
        }));
    };

    $scope.isMapping = function (tagKey, configId) {
        if (tagKey === 'ALL') {
            return true;
        }
        for (let i = 0; i < $scope.mappings.length; i++) {
            let mapping = $scope.mappings[i];
            if (mapping.tagKey === tagKey && mapping.ticketConfigId === configId) {
                return true;
            }
        }
        return false;
    };

    $scope.selectTag = function (key) {
        $scope.all = false;
        for (let tagKey in $scope.tagMap) {
            $scope.tagMap[tagKey].selected = false;
        }
        let tag = $scope.tagMap[key];
        if (tag && key !== 'ALL') {
            tag.selected = true;
        }

        angular.forEach($scope.configs, function (config) {
            config.show = $scope.isMapping(key, config.id);
        });

        // 选择全部时清空查询条件
        if (key === 'ALL') {
            $scope.param = "";
            $scope.all = true;
        }
    };

    $scope.apply = function (config) {
        $scope.ticketId = null;
        $scope.config = config;
        $scope.formUrl = 'project/html/ticket/ticket-create.html' + '?_t=' + window.appversion;
        $scope.sidenavLoading = HttpUtils.get("form/design/template/get/byModel/" + config.modelId, function (response) {
            $scope.template = response.data;
            if ($scope.template && $scope.template.content) {
                $scope.template.content = angular.fromJson($scope.template.content);
            }
        });
        HttpUtils.get("ticket/uuid", function (response) {
            $scope.ticketId = response.data;
        });
        $scope.toggleForm();
    };

    $scope.cancel = function () {
        $scope.sidenavLoading = HttpUtils.get("/file/delete/key/" + $scope.ticketId);
        $scope.toggleForm();
    };

    $scope.create = function () {
        let config = $scope.config;
        let template = $scope.template;
        if ( !config.modelId) {
            Notification.danger(translator('i18n_ticket_submit_failed_without_form', '提交失败，未绑定表单或流程模型'));
            return;
        }

        let ticket = {
            id: $scope.ticketId,
            name: config.name,
            configId: config.id,
            content: angular.toJson(template.content)
        };
        $scope.sidenavLoading = HttpUtils.post("ticket/create", ticket, function () {
            $scope.config = null;
            $scope.template = null;
            $scope.ticketId = null;
            Notification.success(translator('i18n_ticket_submit_success', '提交成功'));
            $scope.toggleForm();
            // 刷新frame外面的待办列表
            if ($window.parent !== $window) {
                $window.parent.api.tasks();
            }
        }, function (response) {
            Notification.danger(translator('i18n_ticket_submit_failed', '提交失败') + "，" + response.message);
        });
    };

    $scope.listConfig();
    $scope.listTag();
    $scope.listMapping();
    $scope.loadingLayer = Loading.load();

});

TicketApp.controller('TicketCreateCtrl', function ($scope, HttpUtils, Loading) {
    $scope.param = "";

    $scope.listConfig = function () {
        Loading.add(HttpUtils.post("ticket/config/list", {}, function (response) {
            $scope.configs = response.data;
        }));
    };

    $scope.listTag = function () {
        Loading.add(HttpUtils.get("ticket/tag/list/using", function (response) {
            $scope.tags = response.data;
            $scope.tagMap = {};
            angular.forEach($scope.tags, function (tag) {
                $scope.tagMap[tag.tagKey] = tag;
            })
        }));
    };

    $scope.listMapping = function () {
        Loading.add(HttpUtils.get("ticket/tag/mapping/list", function (response) {
            $scope.mappings = response.data;
        }));
    };

    $scope.isMapping = function (tagKey, configId) {
        if (tagKey === 'ALL') {
            return true;
        }
        for (let i = 0; i < $scope.mappings.length; i++) {
            let mapping = $scope.mappings[i];
            if (mapping.tagKey === tagKey && mapping.ticketConfigId === configId) {
                return true;
            }
        }
        return false;
    };

    $scope.selectTag = function (key) {
        for (let tagKey in $scope.tagMap) {
            $scope.tagMap[tagKey].selected = false;
        }
        let tag = $scope.tagMap[key];
        if (tag && key !== 'ALL') {
            tag.selected = true;
        }

        angular.forEach($scope.configs, function (config) {
            config.show = $scope.isMapping(key, config.id);
        });

        // 选择全部时清空查询条件
        if (key === 'ALL') {
            $scope.param = "";
        }
    };

    $scope.listConfig();
    $scope.listTag();
    $scope.listMapping();
    $scope.loadingLayer = Loading.load();

});

TicketApp.controller('TicketListCtrl', function ($scope, HttpUtils, FilterSearch, Notification, $filter) {
    const translator = $filter('translator');
    $scope.init = function () {
        $scope.status = [{name: translator('i18n_status_running', '待处理'), key: "RUNNING"},
            {name: translator('i18n_status_cancel', '已撤销'), key: "CANCEL"},
            {name: translator('i18n_status_complete', '已完成'), key: "COMPLETE"},
            {name: translator('i18n_status_terminated', '已中止'), key: "TERMINATED"},
            {name: translator('i18n_status_reject', '已驳回'), key: "REJECT"}];

        $scope.conditions = [
            {key: "quickSearch", name: translator('i18n_ticket_id', '工单ID'), directive: "filter-contains"},
            {key: "name", name: translator('i18n_ticket_name', '工单名称'), directive: "filter-contains"},
            {key: "creator", name: translator('i18n_ticket_creator', '提交人'), directive: "filter-contains"},
            {
                key: "status", name: translator('i18n_status', '状态'), directive: "filter-select", selects: [
                    {label: translator('i18n_status_running', '待处理'), value: "RUNNING"},
                    {label: translator('i18n_status_complete', '已完成'), value: "COMPLETE"},
                    {label: translator('i18n_status_terminated', '已中止'), value: "TERMINATED"},
                    {name: translator('i18n_status_reject', '已中止'), key: "REJECT"}
                ]
            },
            {key: "time", name: translator('i18n_ticket_create_time', '提交日期'), directive: "filter-date"},
        ];

        $scope.filters = [];

        $scope.columns = [
            {value: translator('i18n_ticket_id', '工单ID'), key: "id", width: '20%', sort: false},
            {value: translator('i18n_ticket_name', '工单名称'), key: "name", width: '15%', sort: false},
            {value: translator('i18n_ticket_creator', '提交人'), key: "creator", width: '10%', sort: false},
            {value: translator('i18n_workspace', "工作空间"), key: "workspace_id", width: '15%', sort: false},
            {value: translator('i18n_status', '状态'), key: "status", width: '10%'},
            {value: translator('i18n_ticket_create_time', '提交日期'), key: "time", width: '15%'},
            {value: translator('i18n_ticket_processing_time', '处理时长'), key: "time", width: '10%'},
            {value: "", sort: false, default: true, width: '5%'}
        ];

        $scope.list();
    };

    $scope.list = function (sortObj) {
        let condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "ticket/list", condition);
    };

    $scope.view = function (item) {
        $scope.item = item;
        $scope.template = null;
        $scope.formUrl = 'project/html/ticket/ticket-view.html' + '?_t=' + window.appversion;
        $scope.sidenavLoading = HttpUtils.get("ticket/get/" + item.id, function (response) {
            $scope.ticket = response.data;
            if ($scope.ticket && $scope.ticket.content) {
                $scope.sidenavLoading = HttpUtils.get("form/design/template/get/byModel/" + item.modelId, function (response) {
                    $scope.template = response.data;
                    $scope.template.content = angular.fromJson($scope.ticket.content);

                });
            }
        });

        $scope.toggleForm();
    };

    $scope.delete = function (item) {
        Notification.confirm(translator('i18n_ticket_delete_confirm', '确认执行删除?'), function () {
            $scope.loadingLayer = HttpUtils.get('ticket/delete/' + item.id, function () {
                $scope.list();
                Notification.success(translator('i18n_ticket_delete_success', '删除成功'));
            }, function (response) {
                Notification.danger(translator('i18n_ticket_delete_failed', '删除失败') + "," + response.message);
            });
        });
    };

    $scope.exportServers = function () {
        var execlName = "工单_" + moment().format("YYYY-MM-DD HH:mm:ss") + ".xls";
        HttpUtils.download("ticket/export",{},execlName,'application/json');
    };

    $scope.init();
});

TicketApp.controller('TicketOwnerCtrl', function ($scope, HttpUtils, FilterSearch, Notification, $filter) {
    const translator = $filter('translator');
    $scope.init = function () {
        $scope.type = "owner";

        $scope.status = [
            {name: translator('i18n_status_running', '待处理'), key: "RUNNING"},
            {name: translator('i18n_status_cancel', '已撤销'), key: "CANCEL"},
            {name: translator('i18n_status_complete', '已完成'), key: "COMPLETE"},
            {name: translator('i18n_status_terminated', '已中止'), key: "TERMINATED"},
            {name: translator('i18n_status_reject', '已驳回'), key: "REJECT"}
        ];

        $scope.conditions = [
            {key: "name", name: translator('i18n_ticket_name', '工单名称'), directive: "filter-contains"},
            {
                key: "status", name: translator('i18n_status', '状态'), directive: "filter-select", selects: [
                    {label: translator('i18n_status_running', '待处理'), value: "RUNNING"},
                    {label: translator('i18n_status_complete', '已完成'), value: "COMPLETE"},
                    {label: translator('i18n_status_terminated', '已中止'), value: "TERMINATED"},
                    {name: translator('i18n_status_reject', '已驳回'), key: "REJECT"}
                ]
            },
            {key: "time", name: translator('i18n_ticket_create_time', '提交日期'), directive: "filter-date"},
        ];

        $scope.filters = [];

        $scope.columns = [
            {value: translator('i18n_ticket_id', '工单ID'), key: "id", width: '20%'},
            {value: translator('i18n_ticket_name', '工单名称'), key: "name", width: '15%'},
            {value: translator('i18n_ticket_creator', '提交人'), key: "creator", width: '10%'},
            {value: translator('i18n_workspace', "工作空间"), key: "workspace_id", width: '15%'},
            {value: translator('i18n_status', '状态'), key: "status", width: '10%'},
            {value: translator('i18n_ticket_create_time', '提交日期'), key: "time", width: '20%'},
            {value: translator('i18n_ticket_processing_time', '处理时长'), key: "time", width: '10%'},
        ];

        $scope.list();
    };

    $scope.list = function (sortObj) {
        let condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "ticket/list", condition);
    };

    $scope.view = function (item) {
        $scope.item = item;
        $scope.template = null;
        $scope.formUrl = 'project/html/ticket/ticket-view.html' + '?_t=' + window.appversion;
        $scope.sidenavLoading = HttpUtils.get("ticket/get/" + item.id, function (response) {
            $scope.ticket = response.data;
            if ($scope.ticket && $scope.ticket.content) {
                $scope.sidenavLoading = HttpUtils.get("form/design/template/get/byModel/" + item.modelId, function (response) {
                    $scope.template = response.data;
                    $scope.template.content = angular.fromJson($scope.ticket.content);
                });
            }
        });

        $scope.toggleForm();
    };

    $scope.cancel = function (item) {
        Notification.confirm(translator('i18n_ticket_cancel_confirm', '确定撤销工单?'), function () {
            let request = {
                ticketId: item.id
            };
            $scope.sidenavLoading = HttpUtils.post("ticket/cancel", request, function () {
                Notification.success(translator('i18n_ticket_cancel_success', '撤销成功'));
                $scope.toggleForm();
                $scope.list();
            }, function (response) {
                Notification.danger(ranslator('i18n_ticket_cancel_failed', '撤销失败') + "，" + response.message);
            });
        });
    };

    $scope.init();
});

var TYPES = {
    CREATE: 'create',
    UPDATE: 'update'
};

TicketApp.controller('TicketWatchListCtrl', function ($scope, HttpUtils,FilterSearch, Notification, $filter) {
    const translator = $filter('translator');
    $scope.init = function () {
        $scope.type = "watchList";
        $scope.TYPES = TYPES;

        /*$scope.status = [{name: "待处理", key: "RUNNING"},
            {name: "已撤销", key: "CANCEL"},
            {name: "已完成", key: "COMPLETE"},
            {name: "已中止", key: "TERMINATED"}];*/

        $scope.conditions = [
            {key: "name", name: translator('i18n_duty_name', '值班人名称'), directive: "filter-contains"},
            {key: "time", name: translator('i18n_duty_date', '值班日期'), directive: "filter-date"},
        ];

        $scope.filters = [];

        $scope.columns = [
            {value: translator('i18n_duty_date', '值班日期'), key: "time", width: '20%'},
            {value: translator('i18n_morning_duty_person', '早班值班人'), key: "day", width: '35%'},
            {value: translator('i18n_night_duty_person', '晚班值班人'), key: "night", width: '35%'},
            {value: "", sort: false, default: true, width: '10%'}
        ];
        $scope.list();
    };

    $scope.list = function (sortObj) {
        $scope.initDayUsers();
        let condition = FilterSearch.convert($scope.filters);
        if (sortObj) {
            $scope.sort = sortObj;
        }
        // 保留排序条件，用于分页
        if ($scope.sort) {
            condition.sort = $scope.sort.sql;
        }
        HttpUtils.paging($scope, "ticket/watch/list", condition);
    };

    $scope.openCreate = function () {
        $scope.type = $scope.TYPES.CREATE;
        $scope.tempItem = {};
        $scope.formUrl = 'project/html/ticket/edit-ticket-watch.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.openUpdate = function (item) {
        $scope.type = $scope.TYPES.UPDATE;
        var day = item.dayUserId.substring(1,item.dayUserId.length-1).split(",");
        var night = item.nightUserId.substring(1,item.nightUserId.length-1).split(",");
        item.dayUserId = day;
        item.nightUserId = night;
        console.log(item);
        $scope.tempItem = item;
        $scope.formUrl = 'project/html/ticket/edit-ticket-watch.html' + '?_t=' + window.appversion;
        $scope.toggleForm();
    };

    $scope.close = function () {
        $scope.toggleForm();
    };

    $scope.create = function (item) {
        console.log(item);
        item.dayUserId = JSON.stringify(item.dayUserId);
        item.nightUserId = JSON.stringify(item.nightUserId);
        $scope.sidenavLoading =  HttpUtils.post("ticket/watch/list/create", item, function () {
            Notification.success(translator('i18n_create_success', '创建成功'));
            $scope.toggleForm();
            $scope.list();
        }, function (response) {
            Notification.danger(translator('i18n_create_failed', '创建失败') + "，" + response.message);
        });
    };

    $scope.update = function (item) {
        console.log(item);
         item.dayUserId = JSON.stringify(item.dayUserId);
        item.nightUserId = JSON.stringify(item.nightUserId);
        $scope.sidenavLoading = HttpUtils.post("ticket/watch/list/update",item, function () {
            Notification.success(translator('i18n_ticket_save_success', '保存成功'));
            $scope.toggleForm();
            $scope.list();
        }, function (response) {
            Notification.danger(translator('i18n_ticket_save_failed', '保存失败') + "，" + response.message);
        });
    };

    $scope.delete = function (item) {
        Notification.confirm(translator('i18n_ticket_delete_confirm', '确认执行删除?'), function () {
            $scope.sidenavLoading = HttpUtils.get("ticket/watch/list/delete/" + item.id, function () {
                Notification.success(translator('i18n_ticket_delete_success', '删除成功'));
                $scope.list();
            }, function (response) {
                console.log(response);
                Notification.danger(translator('i18n_ticket_delete_failed', '删除失败') + "," + response.message);
            });
        });
    };

    $scope.initDayUsers = function () {
        $scope.loadingLayer = HttpUtils.get('ticket/watch/dayUsers', function (response) {
            $scope.dayUsers = [];
            angular.forEach(response.data, function (model) {
                var obj = {key: model.id, name: model.name};
                $scope.dayUsers.push(obj);
            });
        });
    };


    $scope.init();

});