(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderFrequencyDeleteController',OrderFrequencyDeleteController);

    OrderFrequencyDeleteController.$inject = ['$uibModalInstance', 'entity', 'OrderFrequency'];

    function OrderFrequencyDeleteController($uibModalInstance, entity, OrderFrequency) {
        var vm = this;

        vm.orderFrequency = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OrderFrequency.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
