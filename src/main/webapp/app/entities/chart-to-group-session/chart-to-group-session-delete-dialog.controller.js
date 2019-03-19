(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToGroupSessionDeleteController',ChartToGroupSessionDeleteController);

    ChartToGroupSessionDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartToGroupSession'];

    function ChartToGroupSessionDeleteController($uibModalInstance, entity, ChartToGroupSession) {
        var vm = this;

        vm.chartToGroupSession = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartToGroupSession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
