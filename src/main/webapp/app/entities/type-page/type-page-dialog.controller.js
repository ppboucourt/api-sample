(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePageDialogController', TypePageDialogController);

    TypePageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePage'];

    function TypePageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePage) {
        var vm = this;

        vm.typePage = entity;
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
            if (vm.typePage.id !== null) {
                TypePage.update(vm.typePage, onSaveSuccess, onSaveError);
            } else {
                TypePage.save(vm.typePage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
