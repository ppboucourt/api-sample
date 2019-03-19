(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DietDialogController', DietDialogController);

    DietDialogController.$inject = ['$timeout', '$scope', 'entity', '$uibModalInstance', 'Diet', 'TypeFoodDiets'];

    function DietDialogController($timeout, $scope, entity, $uibModalInstance, Diet, TypeFoodDiets) {
        var vm = this;

        vm.diet = entity;
        vm.form = {};
        vm.clear = clear;
        vm.save = save;
        vm.typeFoodDiets = TypeFoodDiets.query();

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            if (vm.diet.id !== null) {
                Diet.update(vm.diet, onSaveSuccess, onSaveError);
            } else {
                Diet.save(vm.diet, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('bluebookApp:dietUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
