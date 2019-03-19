(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UnassignedResultController', UnassignedResultController);

    UnassignedResultController.$inject = ['$compile','$scope', 'filterRes', 'moment', 'ChartSearch', 'DateUtils',
        'facility', 'PatientResult', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function UnassignedResultController ($compile, $scope, filterRes, moment, ChartSearch, DateUtils,
               facility, PatientResult, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter) {

        var vm = this;

        vm.title = "Unassigned Result";
        vm.patientResults = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.filterRes = filterRes;
        vm.facility = facility;
        vm.charts = [];
        vm.patient = {};

        vm.selected = {};
        vm.selectAll = false;
        vm.toggleAll = toggleAll;
        vm.toggleOne = toggleOne;

        var titleHtml = '<input type="checkbox" ng-model="vm.selectAll" ng-change="vm.toggleAll()">';

        ChartSearch.query(
            {
                query: {
                    bool: {
                        must: [
                            {term: {"facility.id": vm.facility.id}},
                            {term: {"delStatus": false}}
                        ]
                    }
                }
            }, function(result) {
                vm.charts = result;

                    for (var i = 0; i < vm.charts.length; i++) {
                        var middleName = vm.charts[i].patient.middleName?vm.charts[i].patient.middleName:'';
                        vm.charts[i].name = vm.charts[i].patient.firstName + ' ' + middleName + ' ' + vm.charts[i].patient.lastName;
                        vm.charts[i].dob = vm.charts[i].patient.dateBirth;
                    }
            });

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (vm.isFilter) {
                defer.resolve(vm.patientResults);
                vm.isFilter = false;
            } else if (!vm.searchQuery || vm.searchQuery == '') {
                PatientResult.unassigned({id: vm.facility.id}, function(result) {
                        vm.selected = {};
                        vm.selectAll = false;
                        vm.toggleAll = toggleAll;
                        vm.toggleOne = toggleOne;
                        vm.patient = {};
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
            DTColumnBuilder.newColumn(null).withTitle('Patient'). renderWith(function (data, type, full) {
                return data.patientName;
            }),
            DTColumnBuilder.newColumn(null).withTitle('DOB'). renderWith(function (data, type, full) {
                return moment(data.dob).format("M/DD/Y");
            }),
            // DTColumnBuilder.newColumn(null).withTitle('Clinic ID').renderWith(function (data, type, full) {
            //     return data.order.patient.clinic.id;
            // }),
            DTColumnBuilder.newColumn('status').withTitle('Status'),
            // DTColumnBuilder.newColumn(null).withTitle('Collection Date'). renderWith(function (data, type, full) {
            //     return moment(data.collectionDate).format("M/DD/Y");
            // }),
            DTColumnBuilder.newColumn(null).withTitle('Received Date'). renderWith(function (data, type, full) {
                return moment(data.createdDate).format("M/DD/Y");
            }),
            DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
                .renderWith(abnormalHtml),
            // DTColumnBuilder.newColumn('').withTitle('').withOption('width', '155px').notSortable()
            //    .renderWith(actionsHtml),
        ];

        function abnormalHtml(data, type, full, meta){
            if (full.abnormal) {
                return GUIUtils.getStatusTemplate('ABNORMAL', 'danger');
            } else {
                return GUIUtils.getStatusTemplate('NORMAL', 'success');
            }
        }

        // function actionsHtml(data, type, full, meta) {
        //     return '<div>' +
        //         '<div class="btn-group" uib-dropdown>' +
        //         '<a type="button" target="_blank" class="btn btn-default" href="content/finalreport.pdf">View PDF</a>' +
        //         '<button type="button" class="btn btn-default dropdown-toggle" uib-dropdown-toggle ng-disabled="disabled" data-toggle="dropdown" aria-expanded="false">' +
        //         '<span class="caret"></span>' +
        //         '<span class="sr-only">Toggle Dropdown</span>' +
        //         '</button>' +
        //         '<ul class="dropdown-menu" uib-dropdown-menu role="menu">' +
        //         '<li><a ui-sref="patient-result-details({id:' + full.order.patient.id + ', rid:' + full.id + '})">Details</a></li>' +
        //         '</ul>' +
        //         '</div></div>';
        // }

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

        vm.assign = function() {
            if (!vm.patient) {
                return;
            }

            var result = [];
            for (var id in vm.selected) {
                if (vm.selected[id]) {
                    result.push(id);
                }
            }

            if (result.length > 0) {
                vm.isSaving = true;
                PatientResult.assign({ids: result, date: DateUtils.convertLocalDateToServer(vm.oDate1), patientId: vm.patient.id}, function () {
                    vm.dtInstance.reloadData();
                    vm.isSaving = false;
                }, function () {
                })
            }
        }
    }
})();
