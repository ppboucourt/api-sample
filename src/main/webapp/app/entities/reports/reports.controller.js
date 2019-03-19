(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportsController', ReportsController);

    ReportsController.$inject = ['$scope', '$state', 'Reports', 'ReportsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ReportsController ($scope, $state, Reports, ReportsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.reports = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Reports.query(function(result) {
                  vm.reports = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.reports, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('report_name').withTitle('Report_name'),
              DTColumnBuilder.newColumn('template').withTitle('Template'),
              DTColumnBuilder.newColumn('sort_direction').withTitle('Sort_direction'),
              DTColumnBuilder.newColumn('date_range').withTitle('Date_range'),
              DTColumnBuilder.newColumn('start_date').withTitle('Start_date'),
              //DTColumnBuilder.newColumn('end_date').withTitle('End_date'),
              //DTColumnBuilder.newColumn('criteria').withTitle('Criteria'),
              //DTColumnBuilder.newColumn('selection').withTitle('Selection'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Reports.query(function(result) {
                vm.reports = result;
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
