(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToGroupSessionDialogController', ChartToGroupSessionDialogController);

    ChartToGroupSessionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ChartToGroupSession', 'Chart', 'GroupSession'];

    function ChartToGroupSessionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ChartToGroupSession, Chart, GroupSession) {
        var vm = this;

        vm.chartToGroupSession = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();
        vm.groupsessions = GroupSession.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.chartToGroupSession.id !== null) {
                ChartToGroupSession.update(vm.chartToGroupSession, onSaveSuccess, onSaveError);
            } else {
                ChartToGroupSession.save(vm.chartToGroupSession, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:chartToGroupSessionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
