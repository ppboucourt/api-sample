(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToFormDialogController', ChartToFormDialogController);

    ChartToFormDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToForm', 'Chart', 'Forms'];

    function ChartToFormDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToForm, Chart, Forms) {
        var vm = this;

        vm.chartToForm = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();
        vm.forms = Forms.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToForm.id !== null) {
                ChartToForm.update(vm.chartToForm, onSaveSuccess, onSaveError);
            } else {
                ChartToForm.save(vm.chartToForm, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToFormUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
