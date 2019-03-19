(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationDeleteController',TypeMedicationDeleteController);

    TypeMedicationDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeMedication'];

    function TypeMedicationDeleteController($uibModalInstance, entity, TypeMedication) {
        var vm = this;

        vm.typeMedication = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeMedication.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
