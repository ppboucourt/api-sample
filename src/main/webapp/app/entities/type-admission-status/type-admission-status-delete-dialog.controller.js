(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeAdmissionStatusDeleteController',TypeAdmissionStatusDeleteController);

    TypeAdmissionStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeAdmissionStatus'];

    function TypeAdmissionStatusDeleteController($uibModalInstance, entity, TypeAdmissionStatus) {
        var vm = this;

        vm.typeAdmissionStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeAdmissionStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
