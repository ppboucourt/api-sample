(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToIcd10DeleteController',ChartToIcd10DeleteController);

    ChartToIcd10DeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToIcd10'];

    function ChartToIcd10DeleteController($uibModalInstance, entity, ChartToIcd10) {
        var vm = this;

        vm.chartToIcd10 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToIcd10.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
