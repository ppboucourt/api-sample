(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientToShiftDialogController', PatientToShiftDialogController);

    PatientToShiftDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientToShift', 'Shifts', 'Chart'];

    function PatientToShiftDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientToShift, Shifts, Chart) {
        var vm = this;

        vm.patientToShift = entity;
        vm.clear = clear;
        vm.save = save;
        vm.shifts = Shifts.query();
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientToShift.id !== null) {
                PatientToShift.update(vm.patientToShift, onSaveSuccess, onSaveError);
            } else {
                PatientToShift.save(vm.patientToShift, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientToShiftUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
