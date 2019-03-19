(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CorporationDeleteController',CorporationDeleteController);

    CorporationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Corporation'];

    function CorporationDeleteController($uibModalInstance, entity, Corporation) {
        var vm = this;

        vm.corporation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Corporation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
