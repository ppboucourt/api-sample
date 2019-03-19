(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionsComponentsDeleteController',LabRequisitionsComponentsDeleteController);

    LabRequisitionsComponentsDeleteController.$inject = ['$uibModalInstance', 'entity', 'LabRequisitionsComponents'];

    function LabRequisitionsComponentsDeleteController($uibModalInstance, entity, LabRequisitionsComponents) {
        var vm = this;

        vm.labRequisitionsComponents = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LabRequisitionsComponents.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
