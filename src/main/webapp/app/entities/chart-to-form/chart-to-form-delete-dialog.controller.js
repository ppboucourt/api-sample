(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToFormDeleteController',ChartToFormDeleteController);

    ChartToFormDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToForm'];

    function ChartToFormDeleteController($uibModalInstance, entity, ChartToForm) {
        var vm = this;

        vm.chartToForm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToForm.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
