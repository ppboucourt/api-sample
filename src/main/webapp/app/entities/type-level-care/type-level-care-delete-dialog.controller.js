(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeLevelCareDeleteController',TypeLevelCareDeleteController);

    TypeLevelCareDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeLevelCare'];

    function TypeLevelCareDeleteController($uibModalInstance, entity, TypeLevelCare) {
        var vm = this;

        vm.typeLevelCare = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeLevelCare.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
