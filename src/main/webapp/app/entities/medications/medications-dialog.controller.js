(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationsDialogController', MedicationsDialogController);

    MedicationsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medications', 'Orders'];

    function MedicationsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medications, Orders) {
        var vm = this;

        vm.medications = entity;
        vm.clear = clear;
        vm.save = save;
        vm.orders = Orders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medications.id !== null) {
                Medications.update(vm.medications, onSaveSuccess, onSaveError);
            } else {
                Medications.save(vm.medications, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:medicationsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
