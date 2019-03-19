(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeDeleteController',PatientMedicationTakeDeleteController);

    PatientMedicationTakeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientMedicationTake'];

    function PatientMedicationTakeDeleteController($uibModalInstance, entity, PatientMedicationTake) {
        var vm = this;

        vm.patientMedicationTake = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientMedicationTake.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
