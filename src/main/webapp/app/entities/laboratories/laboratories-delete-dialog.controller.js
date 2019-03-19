(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LaboratoriesDeleteController',LaboratoriesDeleteController);

    LaboratoriesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Laboratories'];

    function LaboratoriesDeleteController($uibModalInstance, entity, Laboratories) {
        var vm = this;

        vm.laboratories = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Laboratories.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
