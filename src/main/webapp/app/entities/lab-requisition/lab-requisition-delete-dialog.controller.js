(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionDeleteController',LabRequisitionDeleteController);

    LabRequisitionDeleteController.$inject = ['$uibModalInstance', 'entity', 'LabRequisition'];

    function LabRequisitionDeleteController($uibModalInstance, entity, LabRequisition) {
        var vm = this;

        vm.labRequisition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LabRequisition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
