(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientToShiftController', PatientToShiftController);

    PatientToShiftController.$inject = ['$scope', '$state', 'PatientToShift', 'PatientToShiftSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function PatientToShiftController ($scope, $state, PatientToShift, PatientToShiftSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.patientToShifts = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  PatientToShift.query(function(result) {
                  vm.patientToShifts = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.patientToShifts, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            PatientToShift.query(function(result) {
                vm.patientToShifts = result;
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
