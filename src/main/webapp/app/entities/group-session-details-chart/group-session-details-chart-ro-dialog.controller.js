(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartRODialogController', GroupSessionDetailsChartRODialogController);

    GroupSessionDetailsChartRODialogController.$inject = ['$stateParams', '$uibModalInstance',
        'entity'];

    function GroupSessionDetailsChartRODialogController ($stateParams, $uibModalInstance, entity) {
        var vm = this;
        vm.clear = clear;

        vm.groupSessionDetailsChart = entity;
        // console.log("vm.groupSessionDetailsChart", vm.groupSessionDetailsChart);

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

    }
})();
