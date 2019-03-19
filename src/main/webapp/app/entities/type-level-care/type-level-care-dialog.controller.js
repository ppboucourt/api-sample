(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeLevelCareDialogController', TypeLevelCareDialogController);

    TypeLevelCareDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeLevelCare', 'Facility'];

    function TypeLevelCareDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeLevelCare, Facility) {
        var vm = this;

        vm.typeLevelCare = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilities = Facility.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeLevelCare.id !== null) {
                TypeLevelCare.update(vm.typeLevelCare, onSaveSuccess, onSaveError);
            } else {
                TypeLevelCare.save(vm.typeLevelCare, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeLevelCareUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
