(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceLevelDialogController', InsuranceLevelDialogController);

    InsuranceLevelDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceLevel', 'Insurance', 'Corporation'];

    function InsuranceLevelDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceLevel, Insurance, Corporation) {
        var vm = this;

        vm.insuranceLevel = entity;
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
            if (vm.insuranceLevel.id !== null) {
                InsuranceLevel.update(vm.insuranceLevel, onSaveSuccess, onSaveError);
            } else {
                InsuranceLevel.save(vm.insuranceLevel, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceLevelUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
