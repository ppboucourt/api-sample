(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDosageDialogController', TypeDosageDialogController);

    TypeDosageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeDosage'];

    function TypeDosageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeDosage) {
        var vm = this;

        vm.typeDosage = entity;
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
            if (vm.typeDosage.id !== null) {
                TypeDosage.update(vm.typeDosage, onSaveSuccess, onSaveError);
            } else {
                TypeDosage.save(vm.typeDosage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeDosageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
