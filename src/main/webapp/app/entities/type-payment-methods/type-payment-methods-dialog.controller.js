(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePaymentMethodsDialogController', TypePaymentMethodsDialogController);

    TypePaymentMethodsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePaymentMethods'];

    function TypePaymentMethodsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePaymentMethods) {
        var vm = this;

        vm.typePaymentMethods = entity;
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
            if (vm.typePaymentMethods.id !== null) {
                TypePaymentMethods.update(vm.typePaymentMethods, onSaveSuccess, onSaveError);
            } else {
                TypePaymentMethods.save(vm.typePaymentMethods, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePaymentMethodsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
