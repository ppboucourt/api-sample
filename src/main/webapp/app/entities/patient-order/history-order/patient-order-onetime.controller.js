(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OneTimeHistoryOrderController', OneTimeHistoryOrderController);

    OneTimeHistoryOrderController.$inject = ['$compile', '$scope', '$state', 'PatientOrder', 'entity',
        '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter', 'moment', '$parse', 'CoreService'];

    function OneTimeHistoryOrderController($compile, $scope, $state, PatientOrder, entity,
                                                    $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, moment, $parse, CoreService) {
        var vm = this;

        vm.patient = entity;
        vm.orders = [];
        vm.search = search;
        vm.dtInstance = {};

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                PatientOrder.ontime_old({id : vm.patient.id}, function (result) {
                    vm.orders = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.patients, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('Order Number'),
            DTColumnBuilder.newColumn(null).withTitle('Status').renderWith(function (data, type, full) {
                return data.ordStatus;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Start Date').renderWith(function (data, type, full) {
                return moment(data.startDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('End Date').renderWith(function (data, type, full) {
                return moment(data.endDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Signed').renderWith(function (data, type, full) {
                return data.signed ? GUIUtils.getStatusTemplate('Yes', 'success') : GUIUtils.getStatusTemplate('No', 'danger');
            }),
            DTColumnBuilder.newColumn(null).withTitle('Signed By').renderWith(function (data, type, full) {
                return data.signed ? 'Dr. ' + data.signedBy.name + ' ' + data.signedBy.lastName : "";
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ui-sref="patient-order-view({oid:' + data.id + '})">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }
    }
})();
