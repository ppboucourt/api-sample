(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TreatmentHistoryController', TreatmentHistoryController);

    TreatmentHistoryController.$inject = ['$scope', 'chart', 'TreatmentHistorySearch', '$q', 'DTColumnBuilder',
        'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile', 'moment'];

    function TreatmentHistoryController($scope, chart, TreatmentHistorySearch, $q, DTColumnBuilder,
                                        DTOptionsBuilder, GUIUtils, $filter, $compile, moment) {
        var vm = this;
        vm.treatmentHistories = [];
        vm.search = search;
        vm.dtInstance = {};
        vm.chart = chart;

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function () {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                var must = [];
                must.push({term: {"chart.id": vm.chart.id}});
                must.push({term: {"delStatus": false}});

                TreatmentHistorySearch.query({
                    query: {
                        bool: {
                            must: must
                        }
                    }
                }, function(result) {
                    vm.treatmentHistories = result;
                    defer.resolve(result);
                });
            } else {
                defer.resolve($filter('filter')(vm.treatmentHistories, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
            .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
                $compile(nRow)($scope);
            });

        vm.dtColumns = [
            DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn('primaryTherapist').withTitle('Primary Therapist').renderWith(function (data, type, full) {
                return data?data:'Empty';
            }),
            DTColumnBuilder.newColumn('coordinator').withTitle('Coordinator').renderWith(function (data, type, full) {
                return data?data:'Empty';
            }),
            DTColumnBuilder.newColumn('date').withTitle('Date').renderWith(function (data, type, full) {
                return moment(data).format('MM/dd/yyyy');
            }),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                .renderWith(actionsHtml)
        ];

        function actionsHtml(data, type, full, meta){
            var stButtons = '';

            stButtons += '<a class="btn-sm btn-warning" ui-sref="treatment-history-edit({thid:' + data.id + '})">' +
                '   <i class="fa fa-edit"></i></a>&nbsp;';
            stButtons += '<a class="btn-sm btn-danger" ui-sref="treatment-history-delete({thid:' + data.id + '})">' +
                '   <i class="fa fa-trash"></i></a>';

            return stButtons;
        }

        function search() {
            vm.dtInstance.reloadData();
        }

    }
})();
