(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VendorsDeleteController',VendorsDeleteController);

    VendorsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Vendors'];

    function VendorsDeleteController($uibModalInstance, entity, Vendors) {
        var vm = this;

        vm.vendors = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Vendors.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
