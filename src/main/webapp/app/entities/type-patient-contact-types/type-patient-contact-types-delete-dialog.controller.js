(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactTypesDeleteController',TypePatientContactTypesDeleteController);

    TypePatientContactTypesDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePatientContactTypes'];

    function TypePatientContactTypesDeleteController($uibModalInstance, entity, TypePatientContactTypes) {
        var vm = this;

        vm.typePatientContactTypes = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePatientContactTypes.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
