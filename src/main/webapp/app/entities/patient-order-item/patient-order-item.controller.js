(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderItemController', PatientOrderItemController);

    PatientOrderItemController.$inject = ['$scope', '$state', 'PatientOrderItem', 'PatientOrderItemSearch', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function PatientOrderItemController($scope, $state, PatientOrderItem, PatientOrderItemSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter) {
        var vm = this;


        vm.patientOrderItems = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};

        // loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                // PatientOrderItem.all(function (result) {
                //     vm.patientOrderItems = result;
                //     defer.resolve(result);
                // });

                vm.patientOrderItems = [];
            } else {
                defer.resolve($filter('filter')(vm.patientOrderItems, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
            DTColumnBuilder.newColumn('groupNumberId').withTitle('GroupNumberId'),
            DTColumnBuilder.newColumn('collected').withTitle('Collected'),
            DTColumnBuilder.newColumn('collectedDate').withTitle('CollectedDate'),
            DTColumnBuilder.newColumn('scheduleDate').withTitle('ScheduleDate'),
            DTColumnBuilder.newColumn('sent').withTitle('Sent'),
            //DTColumnBuilder.newColumn('sentDate').withTitle('SentDate'),
            //DTColumnBuilder.newColumn('readyStatus').withTitle('ReadyStatus'),
            //DTColumnBuilder.newColumn('canceled').withTitle('Canceled'),
            //DTColumnBuilder.newColumn('icd10Codes').withTitle('Icd10Codes'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function loadAll() {
            // PatientOrderItem.query(function(result) {
            //     vm.patientOrderItems = result;
            //     vm.searchQuery = null;
            // });
        }

        function actionsHtml(data, type, full, meta) {
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.rmConfig1 = {
            mondayStart: false,
            initState: "month", /* decade || year || month */
            maxState: "decade",
            minState: "month",
            decadeSize: 12,
            monthSize: 42, /* "auto" || fixed nr. (35 or 42) */
            min: new Date("2010-10-10"),
            max: null,
            format: "MM/dd/yyyy" /* https://docs.angularjs.org/api/ng/filter/date */
        }
        vm.oDate1 = new Date();

    }
})();
