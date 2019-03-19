(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PayerDeleteController',PayerDeleteController);

    PayerDeleteController.$inject = ['$uibModalInstance', 'entity', 'Payer'];

    function PayerDeleteController($uibModalInstance, entity, Payer) {
        var vm = this;

        vm.payer = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Payer.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
