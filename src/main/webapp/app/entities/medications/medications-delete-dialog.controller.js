(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationsDeleteController',MedicationsDeleteController);

    MedicationsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medications'];

    function MedicationsDeleteController($uibModalInstance, entity, Medications) {
        var vm = this;

        vm.medications = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medications.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
