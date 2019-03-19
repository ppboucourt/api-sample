(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Order_typeDeleteController',Order_typeDeleteController);

    Order_typeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Order_type'];

    function Order_typeDeleteController($uibModalInstance, entity, Order_type) {
        var vm = this;

        vm.order_type = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Order_type.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
