(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabProfileDeleteController',LabProfileDeleteController);

    LabProfileDeleteController.$inject = ['$uibModalInstance', 'entity', 'LabProfile'];

    function LabProfileDeleteController($uibModalInstance, entity, LabProfile) {
        var vm = this;

        vm.labProfile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            LabProfile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
