(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationRoutesDialogController', TypeMedicationRoutesDialogController);

    TypeMedicationRoutesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeMedicationRoutes'];

    function TypeMedicationRoutesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeMedicationRoutes) {
        var vm = this;

        vm.typeMedicationRoutes = entity;
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
            if (vm.typeMedicationRoutes.id !== null) {
                TypeMedicationRoutes.update(vm.typeMedicationRoutes, onSaveSuccess, onSaveError);
            } else {
                TypeMedicationRoutes.save(vm.typeMedicationRoutes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeMedicationRoutesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
