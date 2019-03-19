(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OneTimeCurrentOrderController', OneTimeCurrentOrderController);

    OneTimeCurrentOrderController.$inject = ['$compile', '$scope', '$rootScope', '$state', 'PatientOrder', 'chart',
        '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'moment', '$timeout', 'CoreService'];

    function OneTimeCurrentOrderController($compile, $scope, $rootScope, $state, PatientOrder, chart,
                                           $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, moment, $timeout, CoreService) {
        var vm = this;
        vm.chart = chart;
        vm.orders = [];
        vm.search = search;
        vm.newOrder = false;
        vm.dtInstance = {};
        vm.hideAddEditPanel = hideAddEditPanel;
        function hideAddEditPanel() {
            vm.newOrder = false;
            vm.editOrder = false;
            vm.search();
        }

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:clickTabLabOrders', function (event, data) {
            hideAddEditPanel();
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:orderAdded', function (event, data) {
            vm.newOrder = false;
            vm.search();
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:orderCancelled', function (event, data) {
            vm.newOrder = false;
        }));

        vm.addNewOrder = function () {
            $rootScope.newOrder = true;
            vm.newOrder = true;
            vm.editOrder = false;
        }

        vm.updateOrder = function (id) {
            vm.editOrder = false;
            vm.newOrder = false;
            $timeout(function () {
                $rootScope.patientOrder = _.find(vm.orders, function (row) {
                    return row.id === id;
                });
                vm.editOrder = true;
            }, 50)
        }

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:orderEdited', function (event, data) {
            vm.editOrder = false;
            vm.search();
        }));

        $scope.$on('$destroy', $rootScope.$on('bluebookApp:cancelPatientOrder', function (event, data) {
            vm.editOrder = false;
            vm.search();
        }));

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientOrder.orders({id: vm.chart.id}, function (result) {
                    vm.orders = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.orders, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('Order Number'),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return moment(data.startDate).format("MM/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return moment(data.endDate).format("MM/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Signed').renderWith(function (data, type, full) {
                return data.signed ? GUIUtils.getStatusTemplate('Yes', 'success') : GUIUtils.getStatusTemplate('No', 'danger');
            }),
            DTColumnBuilder.newColumn(null).withTitle('Signed By').renderWith(function (data, type, full) {
                return data.signedBy ? 'Dr. ' + data.signedBy.firstName + ' ' + data.signedBy.lastName : "";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            // stButtons += '<a class="btn-sm btn-warning" ui-sref="patient-orders-update({oid:' + data.id + '})">' +
            //     '   <i class="fa fa-edit"></i></a>&nbsp;';
            //
            stButtons += '<a class="btn-sm btn-warning" ng-click="vm.updateOrder(' + data.id + ')">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }
    }
})();
