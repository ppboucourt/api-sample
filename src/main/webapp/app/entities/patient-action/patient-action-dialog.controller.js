(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionDialogController', PatientActionDialogController);

    PatientActionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientAction', 'PatientActionPre', 'Via', 'Chart', 'Employee'];

    function PatientActionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientAction, PatientActionPre, Via, Chart, Employee) {
        var vm = this;

        vm.patientAction = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patientactionpres = PatientActionPre.query();
        vm.vias = Via.query();
        vm.charts = Chart.query();
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientAction.id !== null) {
                PatientAction.update(vm.patientAction, onSaveSuccess, onSaveError);
            } else {
                PatientAction.save(vm.patientAction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientActionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.signatureDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
