(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEmployeeController', TypeEmployeeController);

    TypeEmployeeController.$inject = ['$scope', '$state', 'TypeEmployee', 'TypeEmployeeSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function TypeEmployeeController ($scope, $state, TypeEmployee, TypeEmployeeSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Type Employees';
        vm.typeEmployees = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypeEmployee.query(function(result) {
                  vm.typeEmployees = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typeEmployees, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('name').withTitle('Name'),
              DTColumnBuilder.newColumn('description').withTitle('Description'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypeEmployee.query(function(result) {
                vm.typeEmployees = result;
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
