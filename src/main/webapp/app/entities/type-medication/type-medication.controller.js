(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationController', TypeMedicationController);

    TypeMedicationController.$inject = ['$scope', '$state', 'TypeMedication', 'TypeMedicationSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function TypeMedicationController ($scope, $state, TypeMedication, TypeMedicationSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.typeMedications = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  TypeMedication.query(function(result) {
                  vm.typeMedications = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.typeMedications, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('chemical_medication').withTitle('Chemical_medication'),
              DTColumnBuilder.newColumn('trade_name').withTitle('Trade_name'),
              DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            TypeMedication.query(function(result) {
                vm.typeMedications = result;
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
