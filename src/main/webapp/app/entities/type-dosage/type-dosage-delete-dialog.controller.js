(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDosageDeleteController',TypeDosageDeleteController);

    TypeDosageDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeDosage'];

    function TypeDosageDeleteController($uibModalInstance, entity, TypeDosage) {
        var vm = this;

        vm.typeDosage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeDosage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
