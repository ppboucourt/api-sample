(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EmployeeController', EmployeeController);

    EmployeeController.$inject = ['$scope', '$state', 'Employee', 'EmployeeSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter', '$compile'];

    function EmployeeController ($scope, $state, Employee, EmployeeSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter, $compile ) {
        var vm = this;
        vm.title = 'Employees';
        vm.entityClassHumanized = 'Employee';
        vm.entityStateName = 'employee';

        vm.employees = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Employee.query(function(result) {
                  vm.employees = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.employees, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip')/*.withOption('scrollX', '100%')*/
        .withOption('fnRowCallback', function (nRow, aData, iDisplayIndex, iDisplayIndexFull) {
        $compile(nRow)($scope);
        });

        vm.dtColumns = [
              DTColumnBuilder.newColumn('email').withTitle('Email'),
              DTColumnBuilder.newColumn('mobile').withTitle('Mobile'),
              DTColumnBuilder.newColumn('npiNumber').withTitle('NPI'),
              DTColumnBuilder.newColumn('deaNumber').withTitle('DEA'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
              //DTColumnBuilder.newColumn('firstName').withTitle('FirstName'),
              //DTColumnBuilder.newColumn('lastName').withTitle('LastName'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Employee.query(function(result) {
                vm.employees = result;
                vm.searchQuery = null;
            });
        }

        function actionsHtml(data, type, full, meta){
            return GUIUtils.getActionsTemplate(data, $state.current.name, ['update', 'delete']);
        }

        function search() {
        vm.dtInstance.reloadData();
        }

    }
})();
