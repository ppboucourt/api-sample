(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionsComponentsDialogController', LabRequisitionsComponentsDialogController);

    LabRequisitionsComponentsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LabRequisitionsComponents', 'LabRequisition', 'LabCompendium'];

    function LabRequisitionsComponentsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LabRequisitionsComponents, LabRequisition, LabCompendium) {
        var vm = this;

        vm.labRequisitionsComponents = entity;
        vm.clear = clear;
        vm.save = save;
        vm.labrequisitions = LabRequisition.query();
        vm.labcompendiums = LabCompendium.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.labRequisitionsComponents.id !== null) {
                LabRequisitionsComponents.update(vm.labRequisitionsComponents, onSaveSuccess, onSaveError);
            } else {
                LabRequisitionsComponents.save(vm.labRequisitionsComponents, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labRequisitionsComponentsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
