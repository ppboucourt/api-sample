(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceTypeDialogController', InsuranceTypeDialogController);

    InsuranceTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceType', 'Insurance', 'Corporation'];

    function InsuranceTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceType, Insurance, Corporation) {
        var vm = this;

        vm.insuranceType = entity;
        vm.clear = clear;
        vm.save = save;
        vm.insurances = Insurance.query();
        vm.corporations = Corporation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insuranceType.id !== null) {
                InsuranceType.update(vm.insuranceType, onSaveSuccess, onSaveError);
            } else {
                InsuranceType.save(vm.insuranceType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
