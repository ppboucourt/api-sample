(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToActionDialogController', ChartToActionDialogController);

    ChartToActionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToAction', 'Chart', 'Actions'];

    function ChartToActionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToAction, Chart, Actions) {
        var vm = this;

        vm.chartToAction = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.charts = Chart.query();
        vm.actions = Actions.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToAction.id !== null) {
                ChartToAction.update(vm.chartToAction, onSaveSuccess, onSaveError);
            } else {
                ChartToAction.save(vm.chartToAction, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToActionUpdate', result);
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
