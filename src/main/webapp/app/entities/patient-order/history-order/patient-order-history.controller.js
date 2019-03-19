(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderHistoryController', PatientOrderHistoryController);

    PatientOrderHistoryController.$inject = ['$scope', '$state', 'PatientOrder', 'PatientOrderSearch', 'entity'];

    function PatientOrderHistoryController($scope, $state, PatientOrder, PatientOrderSearch, entity) {
        var vm = this;

        vm.patientOrders = [];
        vm.search = search;
        vm.patient = entity;
        vm.activeTab = $state.params.orderType == 'ONE_TIME' ? 0 : $state.orderType == 'STANDING' ? 1 : 2;

        function loadAll() {
            PatientOrder.query(function (result) {
                vm.patientOrders = result;
            });
        }

        function search() {
            if (!vm.searchQuery) {
                return vm.loadAll();
            }
            PatientOrderSearch.query({query: vm.searchQuery}, function (result) {
                vm.patientOrders = result;
            });
        }
    }
})();
