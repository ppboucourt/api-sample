(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeController', PatientMedicationTakeController);

    PatientMedicationTakeController.$inject = ['$scope', '$state', 'PatientMedicationTake', 'PatientMedicationTakeSearch'];

    function PatientMedicationTakeController ($scope, $state, PatientMedicationTake, PatientMedicationTakeSearch) {
        var vm = this;

        vm.patientMedicationTakes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PatientMedicationTake.query(function(result) {
                vm.patientMedicationTakes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PatientMedicationTakeSearch.query({query: vm.searchQuery}, function(result) {
                vm.patientMedicationTakes = result;
            });
        }    }
})();
