(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartDialogController', GroupSessionDetailsChartDialogController);

    GroupSessionDetailsChartDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GroupSessionDetailsChart', 'GroupSessionDetails'];

    function GroupSessionDetailsChartDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GroupSessionDetailsChart, GroupSessionDetails) {
        var vm = this;

        vm.groupSessionDetailsChart = entity;
        vm.clear = clear;
        vm.save = save;
        vm.groupsessiondetails = GroupSessionDetails.query();
        // vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.groupSessionDetailsChart.id !== null) {
                GroupSessionDetailsChart.update(vm.groupSessionDetailsChart, onSaveSuccess, onSaveError);
            } else {
                GroupSessionDetailsChart.save(vm.groupSessionDetailsChart, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:groupSessionDetailsChartUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
