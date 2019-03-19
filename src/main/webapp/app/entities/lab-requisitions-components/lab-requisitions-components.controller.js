(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionsComponentsController', LabRequisitionsComponentsController);

    LabRequisitionsComponentsController.$inject = ['$scope', '$state', 'LabRequisitionsComponents', 'LabRequisitionsComponentsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function LabRequisitionsComponentsController ($scope, $state, LabRequisitionsComponents, LabRequisitionsComponentsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.labRequisitionsComponents = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  LabRequisitionsComponents.query(function(result) {
                  vm.labRequisitionsComponents = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.labRequisitionsComponents, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            LabRequisitionsComponents.query(function(result) {
                vm.labRequisitionsComponents = result;
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
