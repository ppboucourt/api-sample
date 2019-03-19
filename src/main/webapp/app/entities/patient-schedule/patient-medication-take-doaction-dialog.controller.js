(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeDoActionDialogController', PatientMedicationTakeDoActionDialogController);

    PatientMedicationTakeDoActionDialogController.$inject = ['JSignature', '$sessionStorage', '$uibModalInstance', 'patientMedicationTake', 'PatientMedicationTake', 'Employee'];

    function PatientMedicationTakeDoActionDialogController(JSignature, $sessionStorage, $uibModalInstance, patientMedicationTake, PatientMedicationTake,  Employee) {
        var vm = this;

        vm.clear = clear;
        vm.saveTake = saveTake;

        vm.patientMedicationTake = patientMedicationTake;
        vm.takeTitle = $sessionStorage.takeTitle;
        vm.takeStatus = $sessionStorage.takeStatus;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveTake() {
            vm.saving = true;

            if (JSignature.getData(JSignature.exportTypes.NATIVE).length) {
                var sign = JSignature.getData(JSignature.exportTypes.IMAGE_PNG_BASE64);
                vm.patientMedicationTake.patientSignature = sign;
            }

            vm.patientMedicationTake.medicationTakeStatus = vm.takeStatus;

            PatientMedicationTake.update(vm.patientMedicationTake, function (data) {
                vm.saving = false;
                $uibModalInstance.close(true);
            }, function (error) {
                vm.saving = false;
            });

        }

    }
})();
