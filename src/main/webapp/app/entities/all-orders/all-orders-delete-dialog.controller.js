(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllOrdersDeleteController',AllOrdersDeleteController);

    AllOrdersDeleteController.$inject = ['$uibModalInstance', 'entity', 'AllOrders'];

    function AllOrdersDeleteController($uibModalInstance, entity, AllOrders) {
        var vm = this;

        vm.allOrders = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            AllOrders.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
