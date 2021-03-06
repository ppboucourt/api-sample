(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToLevelCareController', ChartToLevelCareController);

    ChartToLevelCareController.$inject = ['$scope', '$state', 'ChartToLevelCare', 'ChartToLevelCareSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function ChartToLevelCareController ($scope, $state, ChartToLevelCare, ChartToLevelCareSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Chart To Level Cares';
        vm.chartToLevelCares = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToLevelCare.query(function(result) {
                  vm.chartToLevelCares = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToLevelCares, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('startDate').withTitle('StartDate'),
              DTColumnBuilder.newColumn('endDate').withTitle('EndDate'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToLevelCare.query(function(result) {
                vm.chartToLevelCares = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['all']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
