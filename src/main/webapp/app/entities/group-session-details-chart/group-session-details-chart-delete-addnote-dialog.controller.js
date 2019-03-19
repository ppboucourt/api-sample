(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartAddNoteController',GroupSessionDetailsChartAddNoteController);

    GroupSessionDetailsChartAddNoteController.$inject = ['$uibModalInstance', 'entity', 'GroupSessionDetailsChart'];

    function GroupSessionDetailsChartAddNoteController($uibModalInstance, entity, GroupSessionDetailsChart) {
        var vm = this;

        vm.groupSessionDetailsChart = entity;
        vm.clear = clear;
        vm.confirmAdd = confirmAdd;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmAdd () {

            vm.groupSessionDetailsChart.notes =  (vm.groupSessionDetailsChart.notes).trim();

            GroupSessionDetailsChart.update(vm.groupSessionDetailsChart,
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
