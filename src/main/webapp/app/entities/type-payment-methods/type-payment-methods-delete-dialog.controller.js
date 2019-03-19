(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePaymentMethodsDeleteController',TypePaymentMethodsDeleteController);

    TypePaymentMethodsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePaymentMethods'];

    function TypePaymentMethodsDeleteController($uibModalInstance, entity, TypePaymentMethods) {
        var vm = this;

        vm.typePaymentMethods = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePaymentMethods.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
