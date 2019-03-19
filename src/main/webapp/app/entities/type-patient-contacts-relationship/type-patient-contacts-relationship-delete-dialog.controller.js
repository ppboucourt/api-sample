(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactsRelationshipDeleteController',TypePatientContactsRelationshipDeleteController);

    TypePatientContactsRelationshipDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePatientContactsRelationship'];

    function TypePatientContactsRelationshipDeleteController($uibModalInstance, entity, TypePatientContactsRelationship) {
        var vm = this;

        vm.typePatientContactsRelationship = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePatientContactsRelationship.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
