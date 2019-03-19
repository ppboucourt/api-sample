(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToIcd10DialogController', ChartToIcd10DialogController);

    ChartToIcd10DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToIcd10', 'Chart', 'Icd10'];

    function ChartToIcd10DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToIcd10, Chart, Icd10) {
        var vm = this;

        vm.chartToIcd10 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();
        vm.icd10s = Icd10.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToIcd10.id !== null) {
                ChartToIcd10.update(vm.chartToIcd10, onSaveSuccess, onSaveError);
            } else {
                ChartToIcd10.save(vm.chartToIcd10, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToIcd10Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
