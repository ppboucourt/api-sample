(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeDoActionDialogController', PatientActionTakeDoActionDialogController);

    PatientActionTakeDoActionDialogController.$inject = ['JSignature', '$sessionStorage', '$uibModalInstance', 'patientActionTake', 'PatientActionTake', 'Employee'];

    function PatientActionTakeDoActionDialogController(JSignature, $sessionStorage, $uibModalInstance, patientActionTake, PatientActionTake,  Employee) {
        var vm = this;

        vm.clear = clear;
        vm.saveTake = saveTake;

        vm.patientActionTake = patientActionTake;
        vm.takeTitle = $sessionStorage.takeTitle;
        vm.takeStatus = $sessionStorage.takeStatus;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function saveTake() {
            vm.saving = true;

            if (JSignature.getData(JSignature.exportTypes.NATIVE).length) {
                var sign = JSignature.getData(JSignature.exportTypes.IMAGE_PNG_BASE64);
                vm.patientActionTake.patientSignature = sign;
            }

            vm.patientActionTake.actionTakeStatus = vm.takeStatus;

            PatientActionTake.update(vm.patientActionTake, function (data) {
                vm.saving = false;
                $uibModalInstance.close(true);
            }, function (error) {
                vm.saving = false;
            });

        }

    }
})();
