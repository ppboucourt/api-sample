(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEthnicityDeleteController',TypeEthnicityDeleteController);

    TypeEthnicityDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeEthnicity'];

    function TypeEthnicityDeleteController($uibModalInstance, entity, TypeEthnicity) {
        var vm = this;

        vm.typeEthnicity = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeEthnicity.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
