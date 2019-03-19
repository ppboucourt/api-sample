(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LabCompendiumDialogController', LabCompendiumDialogController);

    LabCompendiumDialogController.$inject = ['Tube', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'LabCompendium', 'LabRequisition', 'LabRequisitionsComponents', 'Orders', 'LabProfile'];

    function LabCompendiumDialogController (Tube, $timeout, $scope, $stateParams, $uibModalInstance, entity, LabCompendium, LabRequisition, LabRequisitionsComponents, Orders, LabProfile) {
        var vm = this;

        vm.labCompendium = entity;
        vm.clear = clear;
        vm.save = save;
        vm.labrequisitions = LabRequisition.query();
        vm.labrequisitionscomponents = LabRequisitionsComponents.query();
        vm.orders = Orders.query();
        vm.labprofiles = LabProfile.query();
        vm.tubes = Tube.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.labCompendium.id !== null) {
                LabCompendium.update(vm.labCompendium, onSaveSuccess, onSaveError);
            } else {
                LabCompendium.save(vm.labCompendium, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labCompendiumUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
