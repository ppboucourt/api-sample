(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderFrequencyDialogController', OrderFrequencyDialogController);

    OrderFrequencyDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderFrequency', 'PatientOrderTest'];

    function OrderFrequencyDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderFrequency, PatientOrderTest) {
        var vm = this;

        vm.orderFrequency = entity;
        vm.clear = clear;
        vm.save = save;
        vm.patientordertests = PatientOrderTest.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderFrequency.id !== null) {
                OrderFrequency.update(vm.orderFrequency, onSaveSuccess, onSaveError);
            } else {
                OrderFrequency.save(vm.orderFrequency, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:orderFrequencyUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
