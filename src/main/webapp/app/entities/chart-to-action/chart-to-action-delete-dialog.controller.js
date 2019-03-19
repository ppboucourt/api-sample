(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToActionDeleteController',ChartToActionDeleteController);

    ChartToActionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToAction'];

    function ChartToActionDeleteController($uibModalInstance, entity, ChartToAction) {
        var vm = this;

        vm.chartToAction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToAction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
