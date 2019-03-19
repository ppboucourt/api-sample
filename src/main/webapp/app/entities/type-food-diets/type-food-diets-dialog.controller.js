(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFoodDietsDialogController', TypeFoodDietsDialogController);

    TypeFoodDietsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeFoodDiets'];

    function TypeFoodDietsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeFoodDiets) {
        var vm = this;

        vm.typeFoodDiets = entity;
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
            if (vm.typeFoodDiets.id !== null) {
                TypeFoodDiets.update(vm.typeFoodDiets, onSaveSuccess, onSaveError);
            } else {
                TypeFoodDiets.save(vm.typeFoodDiets, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeFoodDietsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
