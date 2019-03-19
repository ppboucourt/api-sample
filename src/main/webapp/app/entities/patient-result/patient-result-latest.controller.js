(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultLatestController', PatientResultLatestController);

    PatientResultLatestController.$inject = ['facility','$compile','$scope', '$state', 'PatientResult', 'filterRes', 'moment',
        'PatientResultSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', 'PatientResultFile'];

    function PatientResultLatestController (facility, $compile, $scope, $state, PatientResult, filterRes, moment,
                                      PatientResultSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, PatientResultFile) {
        var vm = this;

        vm.patientResults = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.facility = facility;
        vm.filterRes = filterRes;

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (vm.isFilter) {
                defer.resolve(vm.patientResults);
                vm.isFilter = false;
            } else if (!vm.searchQuery || vm.searchQuery == '') {
                  PatientResult.byClinic({id: vm.facility.id}, function(result) {
                  vm.patientResults = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.patientResults, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip').withOption('aaSorting', []).withOption('fnRowCallback',
            function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('id').withTitle('ID'),
            DTColumnBuilder.newColumn('patientName').withTitle('Patient Name'),
            DTColumnBuilder.newColumn(null).withTitle('DOB'). renderWith(function (data, type, full) {
                return moment(data.dob).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Barcode').renderWith(function (data, type, full) {
                return data.accessionNumber;
            }),
            DTColumnBuilder.newColumn(null).withTitle('Order ID').renderWith(function (data, type, full) {
                return data.order ? data.order.id : "";
            }),
            DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Collection Date'). renderWith(function (data, type, full) {
                return moment(data.collectionDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn(null).withTitle('Received Date'). renderWith(function (data, type, full) {
                return moment(data.createdDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
                .renderWith(abnormalHtml),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function abnormalHtml(data, type, full, meta){
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
                '<li><a ui-sref="patient-result-details({id: ' +  full.order.chartId + ', rid:' + full.id + '})">Details</a></li>' +
                '</ul>' +
                '</div></div>';
        }

        function search() {
            vm.dtInstance.reloadData();
        }

        vm.popoverFilterEnable = popoverFilterEnable;
        vm.showFilter = false;

        function popoverFilterEnable(){
            vm.showFilter = !vm.showFilter;
        }

        vm.isFilter = false;
        vm.filter = filter;

        function filter() {
            vm.isFilter = true;

            var must = [];
            must.push({term: {"order.chart.facility.id": vm.facility.id}});
            must.push({term: {"delStatus": false}});

            if (vm.filterRes.patientName != null && vm.filterRes.patientName.length > 0){
                must.push({match: {patientName: {query :vm.filterRes.patientName, fuzziness: 2}}});
            }

            if (vm.filterRes.status != null && vm.filterRes.status.length > 0){
                must.push({match: {status: {query : vm.filterRes.status}}});
            }

            if (vm.filterRes.abnormal != null && vm.filterRes.abnormal.length > 0){
                must.push({match: {abnormal: {query : vm.filterRes.abnormal}}});
            }

            if (vm.filterRes.start) {
                must.push({range: {createdDate: {gte :vm.filterRes.start}}});
            }

            if (vm.filterRes.end) {
                var end = new Date(vm.filterRes.end);
                end.setHours(23, 59, 59);
                must.push({range: {createdDate: {lte: end}}});
            }

            PatientResultSearch.query(
                {
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function(result) {
                    vm.patientResults = result;
                    vm.dtInstance.reloadData();
                });
        }

        vm.datePickerOpenStatus = {};
        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        vm.openCalendar = openCalendar;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        vm.clear = clear;

        function clear() {
            vm.filterRes = {
                patientName: null,
                start: null,
                end: null,
                abnormal: null,
                status: null
            };

            vm.isFilter = false;
            vm.dtInstance.reloadData();
        }

        vm.getPDF = function (id) {
            PatientResultFile.getPdf(id);
        }
    }
})();
