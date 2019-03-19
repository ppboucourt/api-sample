(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionsController', ActionsController);

    ActionsController.$inject = ['$scope', '$state', 'Actions', 'ActionsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function ActionsController ($scope, $state, Actions, ActionsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.actions = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Actions.query(function(result) {
                  vm.actions = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.actions, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('action').withTitle('Action'),
              DTColumnBuilder.newColumn('show_in_mars').withTitle('Show_in_mars'),
              DTColumnBuilder.newColumn('prn').withTitle('Prn'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Actions.query(function(result) {
                vm.actions = result;
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
