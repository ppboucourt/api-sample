(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ShiftsDeleteController',ShiftsDeleteController);

    ShiftsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Shifts'];

    function ShiftsDeleteController($uibModalInstance, entity, Shifts) {
        var vm = this;

        vm.shifts = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Shifts.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
