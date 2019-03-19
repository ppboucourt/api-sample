(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEvaluationController', TypeEvaluationController);

    TypeEvaluationController.$inject = ['$scope', '$state', 'TypeEvaluation', 'TypeEvaluationSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypeEvaluationController ($scope, $state, TypeEvaluation, TypeEvaluationSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typeEvaluations = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypeEvaluation.query(function(result) {
                  vm.typeEvaluations = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typeEvaluations, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypeEvaluation.query(function(result) {
                vm.typeEvaluations = result;
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
