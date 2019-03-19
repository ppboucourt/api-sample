(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartController', GroupSessionDetailsChartController);

    GroupSessionDetailsChartController.$inject = ['$scope', '$state', 'GroupSessionDetailsChart', 'GroupSessionDetailsChartSearch',
        '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'chart', '$compile', '$uibModal', 'lodash'];

    function GroupSessionDetailsChartController($scope, $state, GroupSessionDetailsChart, GroupSessionDetailsChartSearch,
                                                $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, chart, $compile,
                                                $uibModal, _) {
        var vm = this;
        vm.chart = chart;

        vm.groupSessionDetailsCharts = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                GroupSessionDetailsChart.getGroupSessionByChartId({id: vm.chart.id}, function (result) {
                    vm.groupSessionDetailsCharts = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.groupSessionDetailsCharts, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });
        ;

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Start Date Time').withOption('width', '155px').renderWith(function (data) {
                return $filter('date')(data.startDateTime, "MM/dd/yyyy HH:mm");
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date Time').withOption('width', '155px').renderWith(function (data) {
                return $filter('date')(data.endDateTime, "MM/dd/yyyy HH:mm");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Duration').withOption('width', '135px').renderWith(function (data) {
                // return moment.utc(moment(data.endDateTime).diff(moment(data.startDateTime))).format("HH:mm:ss");
                var duration = '';
                if (data.endDateTime && data.startDateTime) {
                    duration = moment.duration(moment(data.endDateTime).diff(moment(data.startDateTime))).humanize();
                }
                return (duration) ? '<span class="badge label-info"><i class="fa fa-clock-o"></i> ' + duration + '</span>' : '';
            }),
            DTColumnBuilder.newColumn('employeeName').withTitle('Employee Name'),
            // DTColumnBuilder.newColumn('notes').withTitle('Notes'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            GroupSessionDetailsChart.getGroupSessionByChartId({id: vm.chart.id}, function (result) {
                vm.groupSessionDetailsCharts = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta) {
            // return GUIUtils.getActionsTemplate(data, $state.current.name, ['view']);
            return '<a class="btn-sm btn-primary" ng-click="vm.openModal(' + data.id + ')">' +
                '   <i class="glyphicon glyphicon-eye-open"></i></a>';
        }

        vm.openModal = function (id) {
            //Looking for the group session with id
            var item = _.find(vm.groupSessionDetailsCharts, function (row) {
                return row.id === id;
            });
            if (item) {
                $uibModal.open({
                    templateUrl: 'app/entities/group-session-details-chart/group-session-details-chart-ro-dialog.html',
                    controller: 'GroupSessionDetailsChartRODialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: item
                    }
                }).result.then(function() {
                    // $state.go('group-session-details-chart', null, { reload: 'group-session-details-chart' });
                }, function() {
                    // $state.go('^');
                });
            }
        }
        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
