(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PayerDialogController', PayerDialogController);

    PayerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payer'];

    function PayerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Payer) {
        var vm = this;

        vm.payer = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.payer.id !== null) {
                Payer.update(vm.payer, onSaveSuccess, onSaveError);
            } else {
                Payer.save(vm.payer, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:payerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
