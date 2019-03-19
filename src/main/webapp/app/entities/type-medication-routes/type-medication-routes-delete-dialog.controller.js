(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationRoutesDeleteController',TypeMedicationRoutesDeleteController);

    TypeMedicationRoutesDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeMedicationRoutes'];

    function TypeMedicationRoutesDeleteController($uibModalInstance, entity, TypeMedicationRoutes) {
        var vm = this;

        vm.typeMedicationRoutes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeMedicationRoutes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
