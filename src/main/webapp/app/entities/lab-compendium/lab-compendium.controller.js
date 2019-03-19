(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabCompendiumController', LabCompendiumController);

    LabCompendiumController.$inject = ['$scope', '$state', 'LabCompendium', 'LabCompendiumSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function LabCompendiumController ($scope, $state, LabCompendium, LabCompendiumSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.labCompendiums = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  LabCompendium.query(function(result) {
                  vm.labCompendiums = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.labCompendiums, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('code').withTitle('Code'),
              DTColumnBuilder.newColumn('description').withTitle('Description'),
              DTColumnBuilder.newColumn('units').withTitle('Units'),
              DTColumnBuilder.newColumn('specimen').withTitle('Specimen'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            LabCompendium.query(function(result) {
                vm.labCompendiums = result;
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
