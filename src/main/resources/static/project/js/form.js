/**
 * FIT2CLOUD 表单设计
 */
(function () {
    let F2CForm = angular.module('f2c.form.common', ['f2c.common', 'f2c.form.design', 'f2c.form.runtime']);

    F2CForm.run(function (MenuRouter) {
        let PROCESS_MENUS = [
            {
                title: "表单设计",
                name: "form_edit",
                url: "/form/edit",
                params: {
                    id: "",
                    parent: "",
                    type: ""
                },
                templateUrl: "project/html/form/form-edit.html" + '?_t=' + window.appversion
            }
        ];
        MenuRouter.addStates(PROCESS_MENUS);
    });

    F2CForm.constant("ELEMENT_TYPE", {
        INPUT: "input",
        SELECT: "select",
        TEXTAREA: "textarea",
        CHECKBOX: "checkbox",
        RADIO: "radio",
        DATE: "date",
        LINK: "link",
        SWITCH: "switch",
    });

    F2CForm.constant("EVENT_NAME", {
        INIT: "onInit",
        CLICK: "onClick",
        CHANGE: "onChange",
        BLUR: "onBlur",
        KEY_PRESS: "onKeyPress",
        KEY_UP: "onKeyUp",
    });

    F2CForm.service("EventService", function ($log, Notification) {

        this.create = function (content) {
            try {
                if (content) {
                    return new Function("self", "ctrl", "context", "service", content);
                }
            } catch (e) {
                $log.error("Event error", e);
                Notification.danger("Event error: " + e + " , Open the console to view the error message");
            }
            return null;
        };
        this.fire = function (event, self, ctrl, context, service) {
            try {
                if (event) event(self, ctrl, context, service);
            } catch (e) {
                $log.error("Event error", e);
                Notification.danger("Event error: " + e + " , Open the console to view the error message");
            }
        };
    });

    F2CForm.service("FormService", function ($state, $mdDialog, $document, ElementService, HttpUtils, Notification, $filter) {
        const translator = $filter('translator');
        this.add = function () {
            sessionStorage.setItem("parent", $state.current.name);
            $state.go("form_edit", {id: "", parent: $state.current.name, type: "add"});
        };

        this.edit = function (id) {
            sessionStorage.setItem("parent", $state.current.name);
            $state.go("form_edit", {id: id, parent: $state.current.name, type: "update"});
        };

        this.paging = function ($scope, param) {
            HttpUtils.paging($scope, "form/design/template/list", param);
        };

        this.save = function (template, type, callback) {
            // 清空预览的值
            angular.forEach(template.content.rows, function (row) {
                angular.forEach(row.cells, function (cell) {
                    if (cell.element) {
                        ElementService.clearElement(cell.element);
                    }
                });
            });
            // JSON对象转文本
            template.content = angular.toJson(template.content);
            return HttpUtils.post('form/design/template/' + type, template, function () {
                if (callback) callback();
                Notification.success(translator('i18n_ticket_save_success', '保存成功'));
            }, function (response) {
                Notification.danger(translator('i18n_ticket_save_failed', '保存失败') + "，" + response.message);
            });
        };

        this.copy = function (oldId, newId, callback) {
            return HttpUtils.get('form/design/template/copy/' + oldId + "/" + newId, function () {
                if (callback) callback();
                Notification.success(translator('i18n_form_copy_success', '复制成功'));
            }, function (response) {
                Notification.danger(translator('i18n_form_copy_failed', '复制失败') + "，" + response.message);
            });
        };

        this.delete = function (item, callback) {
            return HttpUtils.get('form/design/template/delete/' + item.id, function () {
                if (callback) callback();
                Notification.success(translator('i18n_ticket_delete_success', '删除成功'));
            }, function (response) {
                Notification.danger(translator('i18n_ticket_delete_failed', '删除失败') + "," + response.message);
            });
        };

        this.publish = function (item, callback) {
            return HttpUtils.get('form/design/template/publish/' + item.id, function () {
                if (callback) callback();
                Notification.success(translator('i18n_form_publish_success', '发布成功'));
            }, function (response) {
                Notification.danger(translator('i18n_form_publish_failed', '发布失败') + "，" + response.message);
            });
        };

        this.addRow = function (rows, index) {
            if (rows.length === 50) {
                Notification.warn(translator('i18n_form_rows_number_max_limit', '最多50行'));
                return;
            }
            let row = {cells: [{width: "100"}]};
            rows.splice(index + 1, 0, row);
        };

        this.removeRow = function (rows, index) {
            if (rows.length === 1) {
                Notification.warn(translator('i18n_form_rows_number_min_limit', '至少保留1行'));
                return;
            }
            rows.splice(index, 1);
        };

        this.addCell = function (cells, index) {
            if (cells.length === 4) {
                Notification.warn(translator('i18n_from_cells_number_max_limit', '最多4个单元格'));
                return;
            }
            let cell = {width: "auto"};
            cells.splice(index + 1, 0, cell);
            angular.forEach(cells, function (cell) {
                cell.width = "" + Math.floor(100 / cells.length);
            });
        };

        this.removeCell = function (cells, index) {
            if (cells.length === 1) {
                Notification.warn(translator('i18n_from_cells_number_min_limit', '至少保留1个单元格'));
                return;
            }
            cells.splice(index, 1);
            angular.forEach(cells, function (cell) {
                cell.width = "" + Math.floor(100 / cells.length);
            });
        };

        this.setWidth = function (cell, width) {
            let prompt = {
                title: translator('i18n_form_set_width', '设置宽度'),
                text: translator('i18n_form_set_width_text', '整数: 5的倍数(10 - 90), 33, 66 或者 auto'),
                init: width
            };
            Notification.prompt(prompt, function (result) {
                cell.width = "auto";
                if (!isNaN(result)) {
                    let number = Number(result);
                    number = number < 10 ? 10 : number;
                    number = number > 90 ? 90 : number;
                    if (number === 33 || number === 66 || number % 5 === 0) {
                        cell.width = number;
                    }
                }
            });
        };

        this.deleteElement = function (cell) {
            cell.element = null;
        };

        this.preview = function (locals) {
            $mdDialog.show({
                templateUrl: 'project/html/form/form-preview.html' + '?_t=' + window.appversion,
                parent: angular.element($document[0].body),
                controller: function ($scope, $mdDialog, template, context) {
                    $scope.template = template;
                    $scope.context = context;
                    $scope.close = function () {
                        $scope.template = null;
                        $scope.context = null;
                        $mdDialog.hide();
                    };
                },
                locals: locals,
                clickOutsideToClose: false
            });
        };

        this.editEvent = function (locals) {
            $mdDialog.show({
                templateUrl: 'project/html/form/event-edit.html' + '?_t=' + window.appversion,
                parent: angular.element($document[0].body),
                controller: function ($scope, $mdDialog, EventService, event, name, content) {
                    $scope.event = event;
                    $scope.content = content;
                    $scope.name = name;
                    $scope.close = function () {
                        $mdDialog.hide();
                    };

                    $scope.save = function () {
                        if ($scope.content && EventService.create($scope.content) === null) {
                            return;
                        }
                        $scope.event[$scope.name] = $scope.content;
                        $scope.close();
                    }
                },
                locals: locals,
                clickOutsideToClose: false
            });
        };
    });

    F2CForm.service("ElementService", function (ELEMENT_TYPE, EVENT_NAME, $filter) {
        let self = this;
        const translator = $filter('translator');
        this.uuid = function () {
            return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function (c) {
                let r = Math.random() * 16 | 0, v = c === 'x' ? r : (r & 0x3 | 0x8);
                return v.toString(16);
            });
        };

        this.listElement = function () {
            return [
                {
                    label: translator('i18n_element_type_input', '输入框'),
                    type: ELEMENT_TYPE.INPUT,
                    icon: "input"
                }, {
                    label: translator('i18n_element_type_select', '下拉框'),
                    type: ELEMENT_TYPE.SELECT,
                    icon: "list"
                }, {
                    label: translator('i18n_element_type_textarea', '富文本'),
                    type: ELEMENT_TYPE.TEXTAREA,
                    icon: "text_fields"
                }, {
                    label: translator('i18n_element_type_checkbox', '复选框'),
                    type: ELEMENT_TYPE.CHECKBOX,
                    icon: "check_box"
                }, {
                    label: translator('i18n_element_type_radio', '单选框'),
                    type: ELEMENT_TYPE.RADIO,
                    icon: "radio_button_checked"
                }, {
                    label: translator('i18n_element_type_date', '日期'),
                    type: ELEMENT_TYPE.DATE,
                    icon: "date_range"
                }, {
                    label: translator('i18n_element_type_link', '链接'),
                    type: ELEMENT_TYPE.LINK,
                    icon: "link"
                }, {
                    label: translator('i18n_element_type_switch', '开关'),
                    type: ELEMENT_TYPE.SWITCH,
                    icon: "power"
                }
            ];
        };

        this.createElement = function (type) {
            let id = self.uuid();
            // 基础属性
            let element = {
                id: id, // 备用ID，用户无法更改, property.id为空时使用
                property: {
                    id: id,
                    type: type,
                    label: translator('i18n_element_title', '标题')
                },
                event: {names: [EVENT_NAME.INIT]}
            };
            switch (type) {
                case ELEMENT_TYPE.INPUT:
                    element.event.names = [EVENT_NAME.CHANGE, EVENT_NAME.BLUR];
                    break;
                case ELEMENT_TYPE.SELECT:
                    element.property.options = [];
                    element.event.names = [EVENT_NAME.CHANGE];
                    break;
                case ELEMENT_TYPE.TEXTAREA:
                    element.property.rows = 3;
                    element.event.names = [EVENT_NAME.CHANGE, EVENT_NAME.BLUR];
                    break;
                case ELEMENT_TYPE.CHECKBOX:
                    element.property.options = [{id: "id", label: translator('i18n_element_title', '标题'), checked: false}];
                    element.event.names = [EVENT_NAME.CHANGE];
                    break;
                case ELEMENT_TYPE.RADIO:
                    element.property.options = [{key: "", value: ""}];
                    element.event.names = [EVENT_NAME.CHANGE];
                    break;
                case ELEMENT_TYPE.DATE:
                    element.event.names = [EVENT_NAME.CHANGE];
                    break;
                case ELEMENT_TYPE.LINK:
                    element.property.label = translator('i18n_element_link_name', '链接名称');
                    element.event.names = [EVENT_NAME.CLICK];
                    break;
                case ELEMENT_TYPE.SWITCH:
                    element.property.invert = false;
                    element.event.names = [EVENT_NAME.CHANGE];
                    break;
            }
            return element;
        };

        this.initElement = function (element) {
            switch (element.property.type) {
                case ELEMENT_TYPE.INPUT:
                    if (element.property.inputType === 'number') {
                        element.property.value = element.property.value ? Number(element.property.value) : 0;
                    }
                    break;
                case ELEMENT_TYPE.CHECKBOX:
                    element.property.value = element.property.options;
                    element.property.getChecked = function (object) {
                        let checked = [];
                        angular.forEach(element.property.options, function (option) {
                            if (option.checked) {
                                if (object === true) {
                                    checked.push(option);
                                } else {
                                    checked.push(option.id);
                                }
                            }
                        });
                        return checked;
                    };
                    break;
            }
        };

        this.clearElement = function (element) {
            switch (element.property.type) {
                case ELEMENT_TYPE.CHECKBOX:
                    angular.forEach(element.property.options, function (option) {
                        option.checked = false;
                        delete option.empty;
                    });
                    break;
                default:
                    delete element.property.value;
                    delete element.property.empty;
                    break;
            }
        }
    });

})();

/**
 * FIT2CLOUD 表单设计-设计时
 */
(function () {

    let F2CDesign = angular.module('f2c.form.design', []);

    F2CDesign.controller('FormListCtrl', function ($scope, $state, FilterSearch, Notification, FormService, $filter) {
        const translator = $filter('translator');
        $scope.conditions = [
            {key: "name", name: translator('i18n_form_name', '表单名称'), directive: "filter-contains"}
        ];

        $scope.filters = [];

        $scope.columns = [
            {value: translator('i18n_form_id', '表单ID'), key: "name"},
            {value: translator('i18n_form_name', '表单名称'), key: "name"},
            {value: translator('i18n_form_version', '表单版本'), key: "version"},
            {value: translator('i18n_form_published', '是否发布'), key: "deployId"}
        ];

        $scope.add = function () {
            FormService.add();
        };

        $scope.edit = function (item) {
            FormService.edit(item.id);
        };

        $scope.copy = function (item) {
            Notification.prompt({title: translator('i18n_form_copy', '复制表单'), text: translator('i18n_form_id', '表单ID')}, function (result) {
                $scope.loadingLayer = FormService.copy(item.id, result, function () {
                    $state.go("form_edit", {id: result, type: "update"});
                });
            });
        };

        $scope.delete = function (item) {
            Notification.confirm(translator('i18n_ticket_delete_confirm', '确认执行删除?'), function () {
                $scope.loadingLayer = FormService.delete(item, function () {
                    $scope.list();
                });
            });
        };

        $scope.publish = function (item) {
            $scope.loadingLayer = FormService.publish(item, function () {
                $scope.list();
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
            FormService.paging($scope, condition);
        };

        $scope.list();
    });

    F2CDesign.controller('FormDesignCtrl', function ($scope, $state, $stateParams, $log, HttpUtils, Notification, ElementService, FormService, EVENT_NAME) {

        $scope.init = function () {
            $scope.parent = $stateParams.parent ? $stateParams.parent : sessionStorage.getItem("parent") || "form_manager";
            $scope.type = $stateParams.type;
            $scope.active = false;
            $scope.formConfigUrl = 'project/html/form/form-config.html' + '?_t=' + window.appversion;
            $scope.formDesignUrl = 'project/html/form/form-design.html' + '?_t=' + window.appversion;

            if (!$scope.type) {
                $scope.back();
                return;
            }
            $scope.elements = ElementService.listElement();
            $scope.getTemplate($stateParams.id);
        };

        $scope.back = function () {
            $state.go($scope.parent);
        };

        $scope.getTemplate = function (id) {
            if ($scope.type === 'update') {
                $scope.loadingLayer = HttpUtils.get("form/design/template/get/" + id, function (response) {
                    $scope.template = response.data;
                    if ($scope.template && $scope.template.content) {
                        $scope.template.content = angular.fromJson($scope.template.content);
                    }
                });
            } else {
                $scope.template = {
                    content: {
                        event: {names: [EVENT_NAME.INIT]},
                        rows: [
                            {
                                cells: [{width: "auto",}]
                            }, {
                                cells: [{width: "auto",}]
                            }
                        ]
                    }
                };
            }
        };

        $scope.save = function () {
            $scope.loadingLayer = FormService.save($scope.template, $scope.type, function () {
                $scope.type = 'update';
                $scope.getTemplate($scope.template.id);
            });
        };

        $scope.preview = function () {
            let locals = {template: $scope.template, context: $scope.context};
            FormService.preview(locals);
        };

        $scope.publish = function () {
            $scope.loadingLayer = FormService.publish($scope.template, function () {
                $scope.type = 'update';
                $scope.getTemplate($scope.template.id);
            });
        };

        $scope.addRow = FormService.addRow;
        $scope.removeRow = FormService.removeRow;
        $scope.addCell = FormService.addCell;
        $scope.removeCell = FormService.removeCell;
        $scope.setWidth = FormService.setWidth;
        $scope.deleteElement = FormService.deleteElement;
        $scope.config = function (item) {
            $scope.active = true;
            $scope.item = item;
            $scope.selected = item.property.id;
            $scope.elementConfigUrl = "project/html/form/element/" + item.property.type + "-config.html" + '?_t=' + window.appversion;
        };
        $scope.editEvent = function (event, name) {
            let locals = {event: event, name: name, content: event[name]};
            FormService.editEvent(locals);
        };

        $scope.init();
    });

    F2CDesign.directive("dragElement", function (DragDrop) {
        return {
            scope: {
                item: "=dragElement",
            },
            link: function ($scope, element) {
                DragDrop.drag(element, function () {
                    return $scope.item;
                });
            }
        };
    });

    F2CDesign.directive("dropElement", function (DragDrop, ElementService, Notification, $filter) {
        const translator = $filter('translator');
        return {
            scope: {
                element: "=dropElement",
            },
            link: function ($scope, element) {
                DragDrop.drop(element, function (source) {
                    if (source.element) { // 单元格控件拖拽
                        let temp = $scope.element;
                        $scope.element = source.element;
                        source.element = temp;
                    } else { // 右侧工具栏控件拖拽
                        if ($scope.element) {
                            Notification.confirm(translator('i18n_form_element_replace_confirm', '确定替换控件?'), function () {
                                $scope.element = ElementService.createElement(source.type);
                            });
                        } else {
                            $scope.element = ElementService.createElement(source.type);
                        }
                    }
                    $scope.$apply();
                });
            }
        };
    });

    F2CDesign.directive("elementDesign", function () {
        return {
            replace: true,
            require: '?^form',
            template: '<div ng-include="url"></div>',
            scope: {
                item: "="
            },
            link: function ($scope, element, attr, ctrl) {
                $scope.url = "project/html/form/element/" + $scope.item.property.type + "-design.html" + '?_t=' + window.appversion;
                $scope.form = ctrl;
            }
        };
    });
})();

/**
 * FIT2CLOUD 表单设计-运行时
 */
(function () {

    let F2CRuntime = angular.module('f2c.form.runtime', []);

    F2CRuntime.directive("formRuntime", function ($injector, $timeout, EventService, EVENT_NAME) {
        return {
            replace: true,
            templateUrl: "project/html/form/form-runtime.html" + '?_t=' + window.appversion,
            scope: {
                template: "=",
                context: "=",
                disabled: "=ngDisabled"
            },
            link: function ($scope) {
                $scope.self = {id: $scope.template.id, name: $scope.template.name, version: $scope.template.version};

                // ctrl在input-runtime等具体控件注册
                $scope.ctrl = {};

                $scope.service = {
                    $http: $injector.get("$http"),
                    notice: $injector.get("Notification"),
                    show: function (id) {
                        $scope.ctrl[id].display = true;
                    },
                    hide: function (id) {
                        $scope.ctrl[id].display = false;
                    },
                    enable: function (id) {
                        $scope.ctrl[id].disabled = false;
                    },
                    disable: function (id) {
                        $scope.ctrl[id].disabled = true;
                    }
                };

                $scope.event = {};
                angular.forEach($scope.template.content.event.names, function (name) {
                    $scope.event[name] = EventService.create($scope.template.content.event[name]);
                });

                $scope.fireEvent = function (name) {
                    EventService.fire($scope.event[name], $scope.self, $scope.ctrl, $scope.context, $scope.service);
                };

                $timeout(function () {
                    if ($scope.event[EVENT_NAME.INIT]) {
                        $scope.fireEvent(EVENT_NAME.INIT);
                    }
                });
            }
        };
    });

    F2CRuntime.directive("elementRuntime", function ($compile, $timeout, ElementService, EventService, EVENT_NAME) {
        return {
            replace: true,
            template: '<div ng-include="url" style="height: 100%"></div>',
            require: '?^form',
            scope: {
                element: "=",
                ctrl: "=",
                context: "=",
                service: "=",
                disabled: "=ngDisabled"
            },
            link: function ($scope, element, attr, ctrl) {
                $scope.url = "project/html/form/element/" + $scope.element.property.type + "-runtime.html" + '?_t=' + window.appversion;
                $scope.EVENT_NAME = EVENT_NAME;
                $scope.form = ctrl;
                ElementService.initElement($scope.element);

                // 构建self
                let id = $scope.element.property.id || $scope.element.id;
                $scope.self = $scope.element.property;
                $scope.ctrl[id] = $scope.self;
                $scope.self.disabled = $scope.disabled;

                // 生成事件
                $scope.event = {};
                angular.forEach($scope.element.event.names, function (name) {
                    $scope.event[name] = EventService.create($scope.element.event[name]);
                });

                $scope.fireEvent = function (name) {
                    EventService.fire($scope.event[name], $scope.self, $scope.ctrl, $scope.context, $scope.service);
                };

                $timeout(function () {
                    if ($scope.event[EVENT_NAME.INIT]) {
                        $scope.fireEvent(EVENT_NAME.INIT);
                    }
                });
            }
        };
    });
})();