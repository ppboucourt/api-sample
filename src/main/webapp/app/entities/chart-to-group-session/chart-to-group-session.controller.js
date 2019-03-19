(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToGroupSessionController', ChartToGroupSessionController);

    ChartToGroupSessionController.$inject = ['$scope', '$state', 'ChartToGroupSession', 'ChartToGroupSessionSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartToGroupSessionController ($scope, $state, ChartToGroupSession, ChartToGroupSessionSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.chartToGroupSessions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToGroupSession.query(function(result) {
                  vm.chartToGroupSessions = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToGroupSessions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToGroupSession.query(function(result) {
                vm.chartToGroupSessions = result;
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
