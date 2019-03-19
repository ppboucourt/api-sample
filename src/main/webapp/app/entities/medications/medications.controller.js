(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationsController', MedicationsController);

    MedicationsController.$inject = ['$scope', '$state', 'Medications', 'MedicationsSearch', '$q', 'DTColumnBuilder' , 'DTOptionsBuilder', 'GUIUtils', '$filter'];

    function MedicationsController ($scope, $state, Medications, MedicationsSearch, $q, DTColumnBuilder, DTOptionsBuilder, GUIUtils, $filter ) {
        var vm = this;


        vm.medications = [];
        vm.search = search;
        vm.loadAll = loadAll;
        vm.dtInstance = {};


        loadAll();

        vm.dtOptions = DTOptionsBuilder.fromFnPromise(function() {
            var defer = $q.defer();
            if (!vm.searchQuery || vm.searchQuery == '') {
                  Medications.query(function(result) {
                  vm.medications = result;
                  defer.resolve(result);
                });
            }else {
                defer.resolve($filter('filter')(vm.medications, vm.searchQuery, undefined));
            }
            return defer.promise;
        }).withPaginationType('full_numbers').withBootstrap().withDOM('tip');

        vm.dtColumns = [
              DTColumnBuilder.newColumn('medication').withTitle('Medication'),
              DTColumnBuilder.newColumn('route').withTitle('Route'),
              DTColumnBuilder.newColumn('dose').withTitle('Dose'),
              DTColumnBuilder.newColumn('dosage_form').withTitle('Dosage_form'),
              DTColumnBuilder.newColumn('frequency').withTitle('Frequency'),
              //DTColumnBuilder.newColumn('duration_in_days').withTitle('Duration_in_days'),
              //DTColumnBuilder.newColumn('prn').withTitle('Prn'),
              //DTColumnBuilder.newColumn('continue_on_discharge').withTitle('Continue_on_discharge'),
              //DTColumnBuilder.newColumn('status').withTitle('Status'),
            DTColumnBuilder.newColumn(null).withTitle('Actions').withOption('width', '155px').notSortable()
                           .renderWith(actionsHtml)
        ];

        function loadAll() {
            Medications.query(function(result) {
                vm.medications = result;
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
