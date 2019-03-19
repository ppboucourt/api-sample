(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMaritalStatusDeleteController',TypeMaritalStatusDeleteController);

    TypeMaritalStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeMaritalStatus'];

    function TypeMaritalStatusDeleteController($uibModalInstance, entity, TypeMaritalStatus) {
        var vm = this;

        vm.typeMaritalStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeMaritalStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
