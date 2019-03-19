(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactTypesDialogController', TypePatientContactTypesDialogController);

    TypePatientContactTypesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePatientContactTypes'];

    function TypePatientContactTypesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePatientContactTypes) {
        var vm = this;

        vm.typePatientContactTypes = entity;
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
            if (vm.typePatientContactTypes.id !== null) {
                TypePatientContactTypes.update(vm.typePatientContactTypes, onSaveSuccess, onSaveError);
            } else {
                TypePatientContactTypes.save(vm.typePatientContactTypes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePatientContactTypesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
