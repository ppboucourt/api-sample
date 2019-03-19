(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Order_typeDialogController', Order_typeDialogController);

    Order_typeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Order_type'];

    function Order_typeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Order_type) {
        var vm = this;

        vm.order_type = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.order_type.id !== null) {
                Order_type.update(vm.order_type, onSaveSuccess, onSaveError);
            } else {
                Order_type.save(vm.order_type, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:order_typeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
