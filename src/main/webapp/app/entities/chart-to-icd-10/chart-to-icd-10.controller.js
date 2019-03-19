(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToIcd10Controller', ChartToIcd10Controller);

    ChartToIcd10Controller.$inject = ['$scope', '$state', 'ChartToIcd10', 'ChartToIcd10Search', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartToIcd10Controller ($scope, $state, ChartToIcd10, ChartToIcd10Search, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.chartToIcd10S = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToIcd10.query(function(result) {
                  vm.chartToIcd10S = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToIcd10S, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToIcd10.query(function(result) {
                vm.chartToIcd10S = result;
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
