(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProgramsDeleteController',TypePatientProgramsDeleteController);

    TypePatientProgramsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePatientPrograms'];

    function TypePatientProgramsDeleteController($uibModalInstance, entity, TypePatientPrograms) {
        var vm = this;

        vm.typePatientPrograms = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePatientPrograms.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
