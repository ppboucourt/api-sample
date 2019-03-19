(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceCarrierDialogController', InsuranceCarrierDialogController);

    InsuranceCarrierDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'InsuranceCarrier', 'Insurance'];

    function InsuranceCarrierDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, InsuranceCarrier, Insurance) {
        var vm = this;

        vm.insuranceCarrier = entity;
        vm.clear = clear;
        vm.save = save;
        vm.insurances = Insurance.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.insuranceCarrier.id !== null) {
                InsuranceCarrier.update(vm.insuranceCarrier, onSaveSuccess, onSaveError);
            } else {
                InsuranceCarrier.save(vm.insuranceCarrier, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:insuranceCarrierUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
