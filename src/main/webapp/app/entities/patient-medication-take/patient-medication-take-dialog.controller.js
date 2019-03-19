(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeDialogController', PatientMedicationTakeDialogController);

    PatientMedicationTakeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientMedicationTake', 'PatientMedicationPres'];

    function PatientMedicationTakeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientMedicationTake, PatientMedicationPres) {
        var vm = this;

        vm.patientMedicationTake = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patientmedicationpres = PatientMedicationPres.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientMedicationTake.id !== null) {
                PatientMedicationTake.update(vm.patientMedicationTake, onSaveSuccess, onSaveError);
            } else {
                PatientMedicationTake.save(vm.patientMedicationTake, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientMedicationTakeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.collectedDate = false;
        vm.datePickerOpenStatus.scheduleDate = false;
        vm.datePickerOpenStatus.sentDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
