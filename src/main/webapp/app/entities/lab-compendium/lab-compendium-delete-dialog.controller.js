(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabCompendiumDeleteController',LabCompendiumDeleteController);

    LabCompendiumDeleteController.$inject = ['$uibModalInstance', 'entity', 'LabCompendium'];

    function LabCompendiumDeleteController($uibModalInstance, entity, LabCompendium) {
        var vm = this;

        vm.labCompendium = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LabCompendium.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
