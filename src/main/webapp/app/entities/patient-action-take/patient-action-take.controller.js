(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeController', PatientActionTakeController);

    PatientActionTakeController.$inject = ['$scope', '$state', 'PatientActionTake', 'PatientActionTakeSearch'];

    function PatientActionTakeController ($scope, $state, PatientActionTake, PatientActionTakeSearch) {
        var vm = this;

        vm.patientActionTakes = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PatientActionTake.query(function(result) {
                vm.patientActionTakes = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PatientActionTakeSearch.query({query: vm.searchQuery}, function(result) {
                vm.patientActionTakes = result;
            });
        }    }
})();
