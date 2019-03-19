(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderTestDeleteController',PatientOrderTestDeleteController);

    PatientOrderTestDeleteController.$inject = ['$uibModalInstance', 'entity'];

    function PatientOrderTestDeleteController($uibModalInstance, entity) {
        var vm = this;

        vm.patientOrderTest = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            $uibModalInstance.close({id: id});
        }
    }
})();
