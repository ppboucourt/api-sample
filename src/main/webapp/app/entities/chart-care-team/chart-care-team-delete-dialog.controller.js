(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartCareTeamDeleteController',ChartCareTeamDeleteController);

    ChartCareTeamDeleteController.$inject = ['$uibModalInstance', 'entity', 'ChartCareTeam'];

    function ChartCareTeamDeleteController($uibModalInstance, entity, ChartCareTeam) {
        var vm = this;

        vm.chartCareTeam = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ChartCareTeam.delete({id: vm.chartCareTeam.id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
