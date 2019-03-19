(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseDialogController', GlucoseDialogController);

    GlucoseDialogController.$inject = ['$timeout', '$scope', '$uibModalInstance', 'entity', 'Glucose', 'GlucoseIntervention'];

    function GlucoseDialogController ($timeout, $scope, $uibModalInstance, entity, Glucose, GlucoseIntervention) {
        var vm = this;

        vm.glucose = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.glucoseinterventions = GlucoseIntervention.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.glucose.id !== null) {
                Glucose.update(vm.glucose, onSaveSuccess, onSaveError);
            } else {
                Glucose.save(vm.glucose, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:glucoseUpdate', result);
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
