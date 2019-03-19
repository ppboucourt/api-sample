(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderItemDeleteController',PatientOrderItemDeleteController);

    PatientOrderItemDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientOrderItem'];

    function PatientOrderItemDeleteController($uibModalInstance, entity, PatientOrderItem) {
        var vm = this;

        vm.patientOrderItem = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientOrderItem.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
