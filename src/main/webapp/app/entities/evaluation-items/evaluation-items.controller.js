(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationItemsController', EvaluationItemsController);

    EvaluationItemsController.$inject = ['$scope', '$state', 'EvaluationItems', 'EvaluationItemsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function EvaluationItemsController ($scope, $state, EvaluationItems, EvaluationItemsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.evaluationItems = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  EvaluationItems.query(function(result) {
                  vm.evaluationItems = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.evaluationItems, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            EvaluationItems.query(function(result) {
                vm.evaluationItems = result;
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
