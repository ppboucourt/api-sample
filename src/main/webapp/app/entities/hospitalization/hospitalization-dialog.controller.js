(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HospitalizationDialogController', HospitalizationDialogController);

    HospitalizationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'hospitalization', 'Hospitalization', 'chart'];

    function HospitalizationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, hospitalization, Hospitalization, chart) {
        var vm = this;

        vm.hospitalization = hospitalization;
        vm.hospitalization.chart = chart;
        vm.form = {};
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.hospitalization.id !== null) {
                Hospitalization.update(vm.hospitalization, onSaveSuccess, onSaveError);
            } else {
                Hospitalization.save(vm.hospitalization, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:hospitalizationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.admissionDate = false;
        vm.datePickerOpenStatus.dischargeDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
