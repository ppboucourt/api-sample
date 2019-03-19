(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DashboardResultController', DashboardResultController);

    DashboardResultController.$inject = ['$compile', '$scope', 'filterRes', 'moment',
        'CoreService', 'PatientResult', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function DashboardResultController($compile, $scope, filterRes, moment,
                                       CoreService, PatientResult, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter) {

        var vm = this;

        vm.patientResults = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.filterRes = filterRes;
        vm.clinic = CoreService.getCurrentClinic();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (vm.isFilter) {
                defer.resolve(vm.patientResults);
                vm.isFilter = false;
            } else if (!vm.searchQuery || vm.searchQuery == '') {
                PatientResult[vm.filterRes.status]({id: vm.clinic.id}, function (result) {
                    vm.patientResults = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.patientResults, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withOption('aaSorting', []).withDOM('tip').withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn(null).withTitle('Patient').renderWith(function (data, type, full) {
                return data.patientName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('DOB').renderWith(function (data, type, full) {
                return moment(data.dob).format("M/DD/Y");
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Clinic ID').renderWith(function (data, type, full) {
            //     return data.order.patient.clinic.id;
            // }),
            DTColumnBuilder.newColumn(null).withTitle('Barcode').renderWith(function (data, type, full) {
                return data.accessionNumber;
            }),
            DTColumnBuilder.newColumn('status').withTitle('Status'),
            // DTColumnBuilder.newColumn(null).withTitle('Collection Date'). renderWith(function (data, type, full) {
            //     return moment(data.collectionDate).format("M/DD/Y");
            // }),
            DTColumnBuilder.newColumn(null).withTitle('Received Date').renderWith(function (data, type, full) {
                return moment(data.createdDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
                .renderWith(abnormalHtml),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml),
        ];

        function abnormalHtml(data, type, full, meta) {
            if (full.abnormal) {
                return GUIUtils.getStatusTemplate('ABNORMAL', 'danger');
            } else {
                return GUIUtils.getStatusTemplate('NORMAL', 'success');
            }
        }

        function actionsHtml(data, type, full, meta) {
            return '<div>' +
                '<div class="btn-group" uib-dropdown>' +
                (full.patientResultFiles && full.patientResultFiles[0] ? '<button type="button" class="btn btn-default" ng-click="vm.getPDF(' + full.patientResultFiles[0].id + ')">View PDF</button>' : '') +
                '<button type="button" class="btn btn-default dropdown-toggle" uib-dropdown-toggle ng-disabled="disabled" data-toggle="dropdown" aria-expanded="false">' +
                '<span class="caret"></span>' +
                '<span class="sr-only">Toggle Dropdown</span>' +
                '</button>' +
                '<ul class="dropdown-menu" uib-dropdown-menu role="menu">' +
                '<li><a ui-sref="patient-result-details({id:' + full.order.patient.id + ', rid:' + full.id + '})">Details</a></li>' +
                '</ul>' +
                '</div></div>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.getPDF = function (id) {
            PatientResult.get_pdf({id: id});
        }
    }
})();
