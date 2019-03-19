(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DrugsDeleteController',DrugsDeleteController);

    DrugsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Drugs'];

    function DrugsDeleteController($uibModalInstance, entity, Drugs) {
        var vm = this;

        vm.drugs = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Drugs.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
