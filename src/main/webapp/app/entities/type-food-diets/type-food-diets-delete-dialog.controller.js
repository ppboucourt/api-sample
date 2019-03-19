(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFoodDietsDeleteController',TypeFoodDietsDeleteController);

    TypeFoodDietsDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeFoodDiets'];

    function TypeFoodDietsDeleteController($uibModalInstance, entity, TypeFoodDiets) {
        var vm = this;

        vm.typeFoodDiets = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeFoodDiets.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
