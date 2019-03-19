(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePreAdmissionStatusDeleteController',TypePreAdmissionStatusDeleteController);

    TypePreAdmissionStatusDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePreAdmissionStatus'];

    function TypePreAdmissionStatusDeleteController($uibModalInstance, entity, TypePreAdmissionStatus) {
        var vm = this;

        vm.typePreAdmissionStatus = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePreAdmissionStatus.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
