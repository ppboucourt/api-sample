(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllergiesDialogController', AllergiesDialogController);

    AllergiesDialogController.$inject = ['entity', '$timeout', '$scope', '$stateParams', '$uibModalInstance', 'Allergies'];

    function AllergiesDialogController(entity, $timeout, $scope, $stateParams, $uibModalInstance, Allergies) {
        var vm = this;

        vm.allergies = entity;
        // vm.allergies.chart = chart;
        vm.form = {};
        vm.clear = clear;
        vm.save = save;

        $timeout(function () {
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function save() {
            vm.isSaving = true;
            // console.log("vm.allergies", vm.allergies);
            if (vm.allergies.id !== null) {
                Allergies.update(vm.allergies, onSaveSuccess, onSaveError);
            } else {
                Allergies.save(vm.allergies, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess(result) {
            $scope.$emit('bluebookApp:allergiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError() {
            vm.isSaving = false;
        }
    }
})();
