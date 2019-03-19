(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationFrequencyDeleteController',MedicationFrequencyDeleteController);

    MedicationFrequencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'MedicationFrequency'];

    function MedicationFrequencyDeleteController($uibModalInstance, entity, MedicationFrequency) {
        var vm = this;

        vm.medicationFrequency = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            MedicationFrequency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
