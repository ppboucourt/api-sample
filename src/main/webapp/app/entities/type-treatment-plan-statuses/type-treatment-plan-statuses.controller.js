(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeTreatmentPlanStatusesController', TypeTreatmentPlanStatusesController);

    TypeTreatmentPlanStatusesController.$inject = ['$scope', '$state', 'TypeTreatmentPlanStatuses', 'TypeTreatmentPlanStatusesSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypeTreatmentPlanStatusesController ($scope, $state, TypeTreatmentPlanStatuses, TypeTreatmentPlanStatusesSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typeTreatmentPlanStatuses = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypeTreatmentPlanStatuses.query(function(result) {
                  vm.typeTreatmentPlanStatuses = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typeTreatmentPlanStatuses, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypeTreatmentPlanStatuses.query(function(result) {
                vm.typeTreatmentPlanStatuses = result;
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
