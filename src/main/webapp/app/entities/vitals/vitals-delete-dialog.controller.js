(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VitalsDeleteController',VitalsDeleteController);

    VitalsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vitals'];

    function VitalsDeleteController($uibModalInstance, entity, Vitals) {
        var vm = this;

        vm.vitals = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vitals.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
