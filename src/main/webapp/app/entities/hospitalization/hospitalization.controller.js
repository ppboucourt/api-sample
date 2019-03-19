(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HospitalizationController', HospitalizationController);

    HospitalizationController.$inject = ['$scope', 'CoreService', 'HospitalizationSearch', '$q', 'DTColumnBuilder', 'DTOptionsBuilder', '$filter', '$compile', 'chart'];

    function HospitalizationController($scope, CoreService, HospitalizationSearch, $q, DTColumnBuilder, DTOptionsBuilder, $filter, $compile, chart) {
        var vm = this;

        vm.hospitalizations = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.chart = chart;

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var must = [];
                must.push({term: {"chart.id": vm.chart.id}});
                must.push({term: {"delStatus": false}});

                HospitalizationSearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function (result) {
                    vm.hospitalizations = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.hospitalizations, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('hospital').withTitle('Hospital'),
            DTColumnBuilder.newColumn('admissionDate').withTitle('Admission Date').renderWith(function (data, type, full) {
                return CoreService.parseToDate(data);
            }),
            DTColumnBuilder.newColumn('admissionDiagnosis').withTitle('Diagnosis').renderWith(function (data, type, full) {
                return data?data:'Empty';
            }),
            DTColumnBuilder.newColumn('admittingPhysician').withTitle('Admitting Physician').renderWith(function (data, type, full) {
                return data?data:'Empty';
            }),
            DTColumnBuilder.newColumn('dischargeTo').withTitle('Discharge To').renderWith(function (data, type, full) {
                return data?data:'Empty';
            }),
            DTColumnBuilder.newColumn('dischargeDate').withTitle('Discharge Date').renderWith(function (data, type, full) {
                return data?CoreService.parseToDate(data):'Empty';
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta) {
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ui-sref="hospitalization-edit({hid:' + data.id + '})">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';
            stButtons += '<a class="btn-sm btn-danger" ui-sref="hospitalization-delete({hid:' + data.id + '})">' +
                '   <i class="fa fa-trash"></i></a>';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
