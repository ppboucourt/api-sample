(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionsDialogController', ActionsDialogController);

    ActionsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Actions', 'ChartToAction', 'Orders'];

    function ActionsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Actions, ChartToAction, Orders) {
        var vm = this;

        vm.actions = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charttoactions = ChartToAction.query();
        vm.orders = Orders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.actions.id !== null) {
                Actions.update(vm.actions, onSaveSuccess, onSaveError);
            } else {
                Actions.save(vm.actions, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:actionsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
