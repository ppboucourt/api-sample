(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMedicationDialogController', TypeMedicationDialogController);

    TypeMedicationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeMedication'];

    function TypeMedicationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeMedication) {
        var vm = this;

        vm.typeMedication = entity;
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
            if (vm.typeMedication.id !== null) {
                TypeMedication.update(vm.typeMedication, onSaveSuccess, onSaveError);
            } else {
                TypeMedication.save(vm.typeMedication, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeMedicationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
