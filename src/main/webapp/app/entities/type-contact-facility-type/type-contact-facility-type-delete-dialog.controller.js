(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeContactFacilityTypeDeleteController',TypeContactFacilityTypeDeleteController);

    TypeContactFacilityTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeContactFacilityType'];

    function TypeContactFacilityTypeDeleteController($uibModalInstance, entity, TypeContactFacilityType) {
        var vm = this;

        vm.typeContactFacilityType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeContactFacilityType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
