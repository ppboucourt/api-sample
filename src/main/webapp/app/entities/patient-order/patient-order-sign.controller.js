(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('SignOrderController', SignOrderController);

    SignOrderController.$inject = ['facility', 'ROLES',
        '$compile', '$scope', 'PatientOrder', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'CoreService', '$filter', 'moment'];

    function SignOrderController(facility, ROLES,
                                 $compile, $scope, PatientOrder, $q, DTColumnBuilder, DTOptionsBuilder, CoreService, $filter, moment) {
        var vm = this;

        vm.orders = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.title = "Physician Review";
        vm.facility = facility;

        vm.selected = {};
        vm.selectAll = false;
        vm.toggleAll = toggleAll;
        vm.toggleOne = toggleOne;

        var titleHtml = '<input type="checkbox" ng-model="vm.selectAll" ng-change="vm.toggleAll()">';

        vm.employee = CoreService.getCurrentEmployee();
        vm.query = 'all-unsigned';

        for (var i = 0; i < vm.employee.user.authorities.length; i++){
            if (vm.employee.user.authorities[i].name == ROLES.ROLE_CLINICAL_DIRECTOR ||
                vm.employee.user.authorities[i].name == ROLES.ROLE_MD ||
                vm.employee.user.authorities[i].name == ROLES.ROLE_PHYSICIAN_ASSISTANCE
            ) {
                vm.query = 'unsigned';
            }
        }

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientOrder[vm.query]({id: vm.facility.id}, function (result) {
                    vm.selected = {};
                    vm.selectAll = false;
                    vm.toggleAll = toggleAll;
                    vm.toggleOne = toggleOne;

                    vm.orders = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.orders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withOption('aaSorting', []).withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            }).withOption('createdRow', function(row, data, dataIndex) {
            $compile(angular.element(row).contents())($scope);
        }).withOption('headerCallback', function(header) {
            if (!vm.headerCompiled) {
                vm.headerCompiled = true;
                $compile(angular.element(header).contents())($scope);
            }
        });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle(titleHtml).notSortable().renderWith(function(data, type, full, meta) {
                vm.selected[full.id] = false;

                return '<input type="checkbox" ng-model="vm.selected[' + data.id + ']" ng-click="vm.toggleOne()">';
            }),
            DTColumnBuilder.newColumn('id').withTitle('Order Number'),
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                var middleName = data.chart.patient.middleName?data.chart.patient.middleName:'';
                return data.chart.patient.firstName + ' ' + middleName + ' ' + data.chart.patient.lastName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('DOB'). renderWith(function (data, type, full) {
                return moment(data.chart.patient.dateBirth).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Status').renderWith(function (data, type, full) {
                return data.ordStatus;
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
            //     return moment(data.startDate).format("M/DD/Y");
            // }),
            // DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
            //     return moment(data.endDate).format("M/DD/Y");
            // }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ui-sref="patient-orders-update({id:' + full.chart.id + ', oid:' + data.id + '})">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        function toggleAll () {
            for (var id in vm.selected) {
                if (vm.selected.hasOwnProperty(id)) {
                    vm.selected[id] = vm.selectAll;
                }
            }
        }

        function toggleOne () {
            for (var id in vm.selected) {
                if (vm.selected.hasOwnProperty(id)) {
                    if(!vm.selected[id]) {
                        vm.selectAll = false;
                        return;
                    }
                }
            }
            vm.selectAll = true;
        }

        vm.signOrders = function() {
            var orders = [];
            for (var id in vm.selected) {
                if (vm.selected[id]) {
                    orders.push(id);
                }
            }

            if (orders.length > 0) {
                vm.isSaving = true;
                PatientOrder.signOrders({ids: orders}, function () {
                    vm.dtInstance.reloadData();
                    vm.isSaving = false;
                }, function () {
                })
            }
        }
    }
})();
