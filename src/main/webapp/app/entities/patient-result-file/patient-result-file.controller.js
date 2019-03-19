(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultFileController', PatientResultFileController);

    PatientResultFileController.$inject = ['$scope', '$state', 'PatientResultFile', 'PatientResultFileSearch'];

    function PatientResultFileController ($scope, $state, PatientResultFile, PatientResultFileSearch) {
        var vm = this;

        vm.patientResultFiles = [];
        vm.search = search;
        vm.loadAll = loadAll;

        loadAll();

        function loadAll() {
            PatientResultFile.query(function(result) {
                vm.patientResultFiles = result;
            });
        }

        function search () {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PatientResultFileSearch.query({query: vm.searchQuery}, function(result) {
                vm.patientResultFiles = result;
            });
        }    }
})();
