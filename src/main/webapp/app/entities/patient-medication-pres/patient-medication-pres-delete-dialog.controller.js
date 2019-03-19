(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationPresDeleteController',PatientMedicationPresDeleteController);

    PatientMedicationPresDeleteController.$inject = ['$uibModalInstance', 'entity'];

    function PatientMedicationPresDeleteController($uibModalInstance, entity) {
        var vm = this;

        vm.patientMedicationPres = entity;
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
