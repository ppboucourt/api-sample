(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDischargeTypeDialogController', TypeDischargeTypeDialogController);

    TypeDischargeTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeDischargeType'];

    function TypeDischargeTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeDischargeType) {
        var vm = this;

        vm.typeDischargeType = entity;
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
            if (vm.typeDischargeType.id !== null) {
                TypeDischargeType.update(vm.typeDischargeType, onSaveSuccess, onSaveError);
            } else {
                TypeDischargeType.save(vm.typeDischargeType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeDischargeTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
