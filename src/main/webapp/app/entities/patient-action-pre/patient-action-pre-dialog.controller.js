(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionPreDialogController', PatientActionPreDialogController);

    PatientActionPreDialogController.$inject = ['canCancel', 'patientAction', 'patientActionPre', '$uibModalInstance', 'PatientActionTake'];

    function PatientActionPreDialogController (canCancel, patientAction, patientActionPre, $uibModalInstance, PatientActionTake) {
        var vm = this;

        vm.patientAction = patientAction;
        vm.patientActionPre = patientActionPre;
        vm.canCancel = canCancel;

        vm.form = {};

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.accept = function () {
            $uibModalInstance.close();
        }

        vm.cancelActionTake = function (item) {
            if (!item.canceled) {
                PatientActionTake.cancel({id: item.id}, function () {
                }, function () {
                    $state.reload("patient-action-update", {oid: vm.patientAction.id});
                });
            }
        }
    }
})();
