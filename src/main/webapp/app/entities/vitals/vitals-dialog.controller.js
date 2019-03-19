(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VitalsDialogController', VitalsDialogController);

    VitalsDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Vitals'];

    function VitalsDialogController ($timeout, $scope,  $uibModalInstance, entity, Vitals) {
        var vm = this;
        vm.vitals = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;

        vm.form = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.vitals.id !== null) {
                // vm.vitals.chartId = chartId;
                Vitals.update(vm.vitals, onSaveSuccess, onSaveError);
            } else {
                // vm.vitals.chart = chart;
                //console.log( vm.vitals);
                Vitals.save(vm.vitals, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:vitalsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.date = false;
        vm.datePickerOpenStatus.time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
