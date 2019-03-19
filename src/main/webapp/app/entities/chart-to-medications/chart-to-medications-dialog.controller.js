(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToMedicationsDialogController', ChartToMedicationsDialogController);

    ChartToMedicationsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToMedications', 'Chart', 'Medications'];

    function ChartToMedicationsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToMedications, Chart, Medications) {
        var vm = this;

        vm.chartToMedications = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.charts = Chart.query();
        vm.medications = Medications.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToMedications.id !== null) {
                ChartToMedications.update(vm.chartToMedications, onSaveSuccess, onSaveError);
            } else {
                ChartToMedications.save(vm.chartToMedications, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToMedicationsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date_prescription = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
