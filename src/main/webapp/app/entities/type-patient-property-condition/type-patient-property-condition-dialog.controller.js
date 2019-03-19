(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientPropertyConditionDialogController', TypePatientPropertyConditionDialogController);

    TypePatientPropertyConditionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePatientPropertyCondition'];

    function TypePatientPropertyConditionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePatientPropertyCondition) {
        var vm = this;

        vm.typePatientPropertyCondition = entity;
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
            if (vm.typePatientPropertyCondition.id !== null) {
                TypePatientPropertyCondition.update(vm.typePatientPropertyCondition, onSaveSuccess, onSaveError);
            } else {
                TypePatientPropertyCondition.save(vm.typePatientPropertyCondition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePatientPropertyConditionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
