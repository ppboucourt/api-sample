(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Icd10Controller', Icd10Controller);

    Icd10Controller.$inject = ['$scope', '$state', 'Icd10', 'Icd10Search', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function Icd10Controller ($scope, $state, Icd10, Icd10Search, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.icd10S = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Icd10.query(function(result) {
                  vm.icd10S = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.icd10S, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('code').withTitle('Code'),
              DTColumnBuilder.newColumn('description').withTitle('Description'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Icd10.query(function(result) {
                vm.icd10S = result;
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
