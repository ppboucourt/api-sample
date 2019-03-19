(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionsDeleteController',ActionsDeleteController);

    ActionsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Actions'];

    function ActionsDeleteController($uibModalInstance, entity, Actions) {
        var vm = this;

        vm.actions = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Actions.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
