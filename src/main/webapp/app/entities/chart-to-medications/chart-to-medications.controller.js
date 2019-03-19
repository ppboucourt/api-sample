(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToMedicationsController', ChartToMedicationsController);

    ChartToMedicationsController.$inject = ['$scope', '$state', 'ChartToMedications', 'ChartToMedicationsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartToMedicationsController ($scope, $state, ChartToMedications, ChartToMedicationsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.chartToMedications = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToMedications.query(function(result) {
                  vm.chartToMedications = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToMedications, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('taken').withTitle('Taken'),
              DTColumnBuilder.newColumn('notes').withTitle('Notes'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('date_prescription').withTitle('Date_prescription'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToMedications.query(function(result) {
                vm.chartToMedications = result;
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
