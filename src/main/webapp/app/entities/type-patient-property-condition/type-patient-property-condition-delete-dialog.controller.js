(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientPropertyConditionDeleteController',TypePatientPropertyConditionDeleteController);

    TypePatientPropertyConditionDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePatientPropertyCondition'];

    function TypePatientPropertyConditionDeleteController($uibModalInstance, entity, TypePatientPropertyCondition) {
        var vm = this;

        vm.typePatientPropertyCondition = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePatientPropertyCondition.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
