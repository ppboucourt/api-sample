(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeDialogController', PatientActionTakeDialogController);

    PatientActionTakeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientActionTake', 'PatientActionPres'];

    function PatientActionTakeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientActionTake, PatientActionPres) {
        var vm = this;

        vm.patientActionTake = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patientactionpres = PatientActionPres.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientActionTake.id !== null) {
                PatientActionTake.update(vm.patientActionTake, onSaveSuccess, onSaveError);
            } else {
                PatientActionTake.save(vm.patientActionTake, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientActionTakeUpdate', result);
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
