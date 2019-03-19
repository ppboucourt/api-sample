(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TreatmentHistoryDialogController', TreatmentHistoryDialogController);

    TreatmentHistoryDialogController.$inject = ['$timeout', '$scope', 'chart', '$uibModalInstance', 'treatmentHistory', 'TreatmentHistory', 'CoreService'];

    function TreatmentHistoryDialogController ($timeout, $scope, chart, $uibModalInstance, treatmentHistory, TreatmentHistory, CoreService) {
        var vm = this;

        vm.treatmentHistory = treatmentHistory;
        vm.treatmentHistory.chart = chart;
        vm.form = {};
        vm.facility = CoreService.getCurrentFacility();
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
            if (vm.treatmentHistory.id !== null) {
                TreatmentHistory.update(vm.treatmentHistory, onSaveSuccess, onSaveError);
            } else {
                TreatmentHistory.save(vm.treatmentHistory, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:treatmentHistoryUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
