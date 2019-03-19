(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToLevelCareDialogController', ChartToLevelCareDialogController);

    ChartToLevelCareDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToLevelCare', 'Chart', 'TypeLevelCare'];

    function ChartToLevelCareDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToLevelCare, Chart, TypeLevelCare) {
        var vm = this;

        vm.chartToLevelCare = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.charts = Chart.query();
        vm.typelevelcares = TypeLevelCare.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToLevelCare.id !== null) {
                ChartToLevelCare.update(vm.chartToLevelCare, onSaveSuccess, onSaveError);
            } else {
                ChartToLevelCare.save(vm.chartToLevelCare, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToLevelCareUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.endDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
