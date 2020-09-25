/**
 * 启动app，加载菜单
 */
let TaskApp = angular.module('TaskApp', ['f2c.form.common', 'f2c.process']);

TaskApp.controller('TicketTaskCtrl', function ($scope, $window, HttpUtils) {

    $scope.init = function () {
        let params = $scope.getParams();
        if (!params.businessKey || !params.taskId) {
            return;
        }
        $scope.remarks = {content: ""};
        $scope.ticketId = params.businessKey;
        $scope.taskId = params.taskId;
        $scope.get($scope.taskId);
    };

    $scope.getParams = function () {
        if (location.search.length <= 1) return {};
        let qs = location.search.substr(1),
            args = {},
            items = qs.length ? qs.split("&") : [],
            item = null,
            len = items.length;

        for (let i = 0; i < len; i++) {
            item = items[i].split("=");
            let name = decodeURIComponent(item[0]),
                value = decodeURIComponent(item[1]);
            if (name) {
                args[name] = value;
            }
        }
        return args;
    };

    $scope.get = function (taskId) {
        $scope.ticket = null;
        $scope.template = null;
        $scope.loadingLayer = HttpUtils.get("ticket/approve/get/" + taskId, function (response) {
            $scope.ticket = response.data;
            if ($scope.ticket && $scope.ticket.content) {
                $scope.loadingLayer = HttpUtils.get("form/design/template/get/byModel/" + $scope.ticket.modelId, function (response) {
                    $scope.template = response.data;
                    $scope.template.content = angular.fromJson($scope.ticket.content);
                });
            }
            $scope.loadingLayer = HttpUtils.get("form/design/template/get/byModel/" + $scope.ticket.modelId+ "/" +taskId, function (response) {
                $scope.templateCu = response.data;
                $scope.templateCu.content = angular.fromJson($scope.templateCu.content);
            });
        });

        HttpUtils.get("flow/runtime/task/" + $scope.taskId, function (response) {
            $scope.task = response.data;
        });
    };

    $scope.complete = function () {
        //将处理环节和申请表单结合
        if(null != $scope.templateCu){
            var arr =[];
            for( var i in $scope.template.content.rows){
                arr.push($scope.template.content.rows[i]);
            }
            for( var j in $scope.templateCu.content.rows){
                arr.push($scope.templateCu.content.rows[j]);
            }
            $scope.template.content.rows = arr;
            $scope.ticket.content =angular.toJson($scope.template.content);
        }

        let request = {
            taskId: $scope.taskId,
            remarks: $scope.remarks.content,
            ticket: $scope.ticket
        };
        $scope.loadingLayer = HttpUtils.post("ticket/complete/", request, function () {
            $scope.refresh();
            $scope.cancel();
        });
    };

    $scope.reject = function () {
        let request = {
            taskId: $scope.taskId,
            remarks: $scope.remarks.content,
            ticket: $scope.ticket
        };
        $scope.loadingLayer = HttpUtils.post("ticket/reject/", request, function () {
            $scope.refresh();
            $scope.cancel();
        });
    };

    $scope.refresh = function () {
        $window.parent.api.tasks();
    };

    $scope.init();
});