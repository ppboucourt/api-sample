(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToActionController', ChartToActionController);

    ChartToActionController.$inject = ['$scope', '$state', 'ChartToAction', 'ChartToActionSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartToActionController ($scope, $state, ChartToAction, ChartToActionSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.chartToActions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToAction.query(function(result) {
                  vm.chartToActions = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToActions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('notes').withTitle('Notes'),
              DTColumnBuilder.newColumn('archived').withTitle('Archived'),
              DTColumnBuilder.newColumn('date_prescription').withTitle('Date_prescription'),
              DTColumnBuilder.newColumn('taken').withTitle('Taken'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToAction.query(function(result) {
                vm.chartToActions = result;
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
