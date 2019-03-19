(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationFrequencyController', MedicationFrequencyController);

    MedicationFrequencyController.$inject = ['$scope', '$state', 'MedicationFrequency', 'MedicationFrequencySearch'];

    function MedicationFrequencyController ($scope, $state, MedicationFrequency, MedicationFrequencySearch) {
        var vm = this;

        vm.medicationFrequencies = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            MedicationFrequency.query(function(result) {
                vm.medicationFrequencies = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            MedicationFrequencySearch.query({query: vm.searchQuery}, function(result) {
                vm.medicationFrequencies = result;
            });
        }    }
})();
