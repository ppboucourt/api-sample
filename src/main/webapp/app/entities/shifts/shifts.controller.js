(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ShiftsController', ShiftsController);

    ShiftsController.$inject = ['$scope', '$state', 'Shifts', 'ShiftsSearch', '$q', 'DTColumnBuilder', 'DTOptionsBuilder',
        'GUIUtils', '$filter', 'CoreService', '$compile', 'lodash'];

    function ShiftsController($scope, $state, Shifts, ShiftsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils,
                              $filter, CoreService, $compile, _) {
        var vm = this;


        vm.shifts = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};
        // vm.currentPatients = Shifts.currentPatients({id: CoreService.getCurrentFacility().id, date: new Date});

        vm.title = 'Shifts';
        vm.entityClassHumanized = 'Shifts';
        vm.entityStateName = 'shifts';

        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                Shifts.query(function (result) {
                    vm.shifts = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.shifts, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withOption('order', []).withOption('createdRow', createdRow).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        function createdRow(row, data, dataIndex) {
            // Recompiling so we can bind Angular directive to the DT
            $compile(angular.element(row).contents())($scope);
        }

        vm.dtColumns = [
            // DTColumnBuilder.newColumn('createdBy').withTitle('Owner'),
            DTColumnBuilder.newColumn(null).withTitle('Name').renderWith(function (data, type, full) {
                return (data) ? (data.lastName + ', ' + data.firstName) : '  ';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Created On').renderWith(function (data, type, full) {
                return moment(data.createdDate).format('MM/DD/YYYY hh:mm a');
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            Shifts.query(function (result) {
                vm.shifts = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta) {
            return '<button class="btn btn-sm btn-primary" type="button" ng-click="vm.viewDetail(\'' + data.id + '\');">' +
                '<i class="fa fa-eye"></i></button>&nbsp;' +
                '<button class="btn btn-sm btn-warning" type="button" ng-click="vm.checkShiftAndEdit(' + data.employeeId + ', \'' + data.createdDate + '\', \'' + data.id + '\');"' +
                'ng-if="!vm.checkCantModifyShift(' + data.employeeId + ', \'' + data.createdDate + '\', \'' + data.id + '\')">' +
                '<i class="fa fa-edit"></i></button>&nbsp;';
        }

        vm.viewDetail = function (id) {
            $state.go('shifts-detail', {id: id, fromDetail: 1});
        }
        vm.checkCantModifyShift = function (employeeId, date, id) {
            var cantModifyShift = false;
            //Checking first employeeId (Need to match with the logged in employee)
            if (employeeId !== CoreService.getCurrentEmployee().id) {
                cantModifyShift = true
            }
            if (!moment(date).isSame(moment(), 'day')) {
                cantModifyShift = true;
            }
            return cantModifyShift;
        }

        vm.checkShiftAndEdit = function (employeeId, date, id) {
            vm.cantModifyShift = vm.checkCantModifyShift(employeeId, date, id);
            if (!vm.cantModifyShift) {
                $state.go('shifts-edit', {id: id, modify: !vm.cantModifyShift ? 1 : 0});
            } else {
                $state.go('shifts-detail', {id: id, fromDetail: 0});
            }

        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
