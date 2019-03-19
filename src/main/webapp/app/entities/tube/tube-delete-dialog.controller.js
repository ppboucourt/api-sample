(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TubeDeleteController',TubeDeleteController);

    TubeDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tube'];

    function TubeDeleteController($uibModalInstance, entity, Tube) {
        var vm = this;

        vm.tube = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tube.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
