(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BedDeleteController',BedDeleteController);

    BedDeleteController.$inject = ['$uibModalInstance', 'entity', 'Bed'];

    function BedDeleteController($uibModalInstance, entity, Bed) {
        var vm = this;

        vm.bed = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Bed.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
