(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ViaDeleteController',ViaDeleteController);

    ViaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Via'];

    function ViaDeleteController($uibModalInstance, entity, Via) {
        var vm = this;

        vm.via = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Via.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
