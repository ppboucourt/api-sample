(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Icd10DialogController', Icd10DialogController);

    Icd10DialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Icd10', 'Chart'];

    function Icd10DialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Icd10, Chart) {
        var vm = this;

        vm.icd10 = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.icd10.id !== null) {
                Icd10.update(vm.icd10, onSaveSuccess, onSaveError);
            } else {
                Icd10.save(vm.icd10, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:icd10Update', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
