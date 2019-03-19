(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderComponentDialogController', OrderComponentDialogController);

    OrderComponentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OrderComponent', 'AllOrders', 'Routes'];

    function OrderComponentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OrderComponent, AllOrders, Routes) {
        var vm = this;

        vm.orderComponent = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.allorders = AllOrders.query();
        vm.routes = Routes.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderComponent.id !== null) {
                OrderComponent.update(vm.orderComponent, onSaveSuccess, onSaveError);
            } else {
                OrderComponent.save(vm.orderComponent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:orderComponentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.administration_time = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
