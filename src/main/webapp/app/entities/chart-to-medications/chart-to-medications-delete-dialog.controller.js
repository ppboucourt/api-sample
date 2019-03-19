(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToMedicationsDeleteController',ChartToMedicationsDeleteController);

    ChartToMedicationsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToMedications'];

    function ChartToMedicationsDeleteController($uibModalInstance, entity, ChartToMedications) {
        var vm = this;

        vm.chartToMedications = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToMedications.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
