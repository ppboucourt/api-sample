(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabRequisitionDialogController', LabRequisitionDialogController);

    LabRequisitionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LabRequisition', 'LabCompendium', 'LabRequisitionsComponents', 'Chart'];

    function LabRequisitionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, LabRequisition, LabCompendium, LabRequisitionsComponents, Chart) {
        var vm = this;

        vm.labRequisition = entity;
        vm.clear = clear;
        vm.save = save;
        vm.labcompendiums = LabCompendium.query();
        vm.labrequisitionscomponents = LabRequisitionsComponents.query();
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.labRequisition.id !== null) {
                LabRequisition.update(vm.labRequisition, onSaveSuccess, onSaveError);
            } else {
                LabRequisition.save(vm.labRequisition, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labRequisitionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
