(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LaboratoriesDialogController', LaboratoriesDialogController);

    LaboratoriesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Laboratories'];

    function LaboratoriesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Laboratories) {
        var vm = this;

        vm.laboratories = entity;
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
            if (vm.laboratories.id !== null) {
                Laboratories.update(vm.laboratories, onSaveSuccess, onSaveError);
            } else {
                Laboratories.save(vm.laboratories, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:laboratoriesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
