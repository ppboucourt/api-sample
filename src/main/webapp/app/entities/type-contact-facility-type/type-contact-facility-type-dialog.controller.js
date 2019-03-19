(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeContactFacilityTypeDialogController', TypeContactFacilityTypeDialogController);

    TypeContactFacilityTypeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeContactFacilityType'];

    function TypeContactFacilityTypeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeContactFacilityType) {
        var vm = this;

        vm.typeContactFacilityType = entity;
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
            if (vm.typeContactFacilityType.id !== null) {
                TypeContactFacilityType.update(vm.typeContactFacilityType, onSaveSuccess, onSaveError);
            } else {
                TypeContactFacilityType.save(vm.typeContactFacilityType, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeContactFacilityTypeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
