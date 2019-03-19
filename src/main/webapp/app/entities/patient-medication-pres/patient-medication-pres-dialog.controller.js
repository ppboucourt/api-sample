(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationPresDialogController', PatientMedicationPresDialogController);

    PatientMedicationPresDialogController.$inject = ['canCancel', 'patientMedication', 'patientMedicationPres', '$uibModalInstance', 'PatientMedicationTake'];

    function PatientMedicationPresDialogController (canCancel, patientMedication, patientMedicationPres, $uibModalInstance, PatientMedicationTake) {
        var vm = this;

        vm.patientMedication = patientMedication;
        vm.patientMedicationPres = patientMedicationPres;
        vm.canCancel = canCancel;

        vm.form = {};

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.accept = function () {
            $uibModalInstance.close();
        }

        vm.cancelMedicationTake = function (item) {
            if (!item.canceled) {
                PatientMedicationTake.cancel({id: item.id}, function () {
                }, function () {
                    $state.reload("patient-medication-update", {oid: vm.patientMedication.id});
                });
            }
        }
    }
})();
