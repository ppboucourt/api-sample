(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProcessDeleteController',TypePatientProcessDeleteController);

    TypePatientProcessDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePatientProcess'];

    function TypePatientProcessDeleteController($uibModalInstance, entity, TypePatientProcess) {
        var vm = this;

        vm.typePatientProcess = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePatientProcess.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
