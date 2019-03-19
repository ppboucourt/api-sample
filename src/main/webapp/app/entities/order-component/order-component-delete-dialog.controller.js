(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderComponentDeleteController',OrderComponentDeleteController);

    OrderComponentDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderComponent'];

    function OrderComponentDeleteController($uibModalInstance, entity, OrderComponent) {
        var vm = this;

        vm.orderComponent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderComponent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
