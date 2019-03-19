(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderItemDialogController', PatientOrderItemDialogController);

    PatientOrderItemDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientOrderItem', 'PatientOrderTest'];

    function PatientOrderItemDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientOrderItem, PatientOrderTest) {
        var vm = this;

        vm.patientOrderItem = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patientordertests = PatientOrderTest.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientOrderItem.id !== null) {
                PatientOrderItem.update(vm.patientOrderItem, onSaveSuccess, onSaveError);
            } else {
                PatientOrderItem.save(vm.patientOrderItem, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientOrderItemUpdate', result);
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
