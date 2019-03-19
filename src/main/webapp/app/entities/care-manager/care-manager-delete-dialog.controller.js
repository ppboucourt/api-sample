(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CareManagerDeleteController',CareManagerDeleteController);

    CareManagerDeleteController.$inject = ['$uibModalInstance', 'entity', 'CareManager'];

    function CareManagerDeleteController($uibModalInstance, entity, CareManager) {
        var vm = this;

        vm.careManager = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CareManager.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
