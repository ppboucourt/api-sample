(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationFrequencyDialogController', MedicationFrequencyDialogController);

    MedicationFrequencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'MedicationFrequency', 'PatientMedicationPrescription'];

    function MedicationFrequencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, MedicationFrequency, PatientMedicationPrescription) {
        var vm = this;

        vm.medicationFrequency = entity;
        vm.clear = clear;
        vm.save = save;
        vm.patientmedicationprescriptions = PatientMedicationPrescription.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medicationFrequency.id !== null) {
                MedicationFrequency.update(vm.medicationFrequency, onSaveSuccess, onSaveError);
            } else {
                MedicationFrequency.save(vm.medicationFrequency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:medicationFrequencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
