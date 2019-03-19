(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToLevelCareDeleteController',ChartToLevelCareDeleteController);

    ChartToLevelCareDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToLevelCare'];

    function ChartToLevelCareDeleteController($uibModalInstance, entity, ChartToLevelCare) {
        var vm = this;

        vm.chartToLevelCare = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToLevelCare.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
