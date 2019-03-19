(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToFormController', ChartToFormController);

    ChartToFormController.$inject = ['$scope', '$state', 'ChartToForm', 'ChartToFormSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ChartToFormController ($scope, $state, ChartToForm, ChartToFormSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.chartToForms = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  ChartToForm.query(function(result) {
                  vm.chartToForms = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.chartToForms, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              DTColumnBuilder.newColumn('signature').withTitle('Signature'),
              DTColumnBuilder.newColumn('signed').withTitle('Signed'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            ChartToForm.query(function(result) {
                vm.chartToForms = result;
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
