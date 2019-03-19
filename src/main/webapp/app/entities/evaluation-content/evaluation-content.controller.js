(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationContentController', EvaluationContentController);

    EvaluationContentController.$inject = ['$scope', '$state', 'EvaluationContent', 'EvaluationContentSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function EvaluationContentController ($scope, $state, EvaluationContent, EvaluationContentSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.evaluationContents = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  EvaluationContent.query(function(result) {
                  vm.evaluationContents = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.evaluationContents, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            EvaluationContent.query(function(result) {
                vm.evaluationContents = result;
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
