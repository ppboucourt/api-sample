(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('WeightDialogController', WeightDialogController);

    WeightDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Weight'];

    function WeightDialogController ($timeout, $scope, $uibModalInstance, entity, Weight) {
        var vm = this;

        vm.weight = entity;
        vm.clear = clear;
        vm.save = save;

        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;


        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.weight.id !== null) {
                vm.weight.bmi=  (vm.weight.weight / ( vm.weight.height * vm.weight.height)) * 100;
                Weight.update(vm.weight, onSaveSuccess, onSaveError);
            } else {
                // vm.weight.chart = chart;
                vm.weight.bmi =  (vm.weight.weight / ( vm.weight.height * vm.weight.height)) * 100;
                Weight.save(vm.weight, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:weightUpdate', result);
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
