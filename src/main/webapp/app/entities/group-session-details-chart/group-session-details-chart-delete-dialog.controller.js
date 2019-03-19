(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartDeleteController',GroupSessionDetailsChartDeleteController);

    GroupSessionDetailsChartDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupSessionDetailsChart'];

    function GroupSessionDetailsChartDeleteController($uibModalInstance, entity, GroupSessionDetailsChart) {
        var vm = this;

        vm.groupSessionDetailsChart = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupSessionDetailsChart.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
