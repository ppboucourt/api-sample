(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEthnicityDialogController', TypeEthnicityDialogController);

    TypeEthnicityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeEthnicity'];

    function TypeEthnicityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeEthnicity) {
        var vm = this;

        vm.typeEthnicity = entity;
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
            if (vm.typeEthnicity.id !== null) {
                TypeEthnicity.update(vm.typeEthnicity, onSaveSuccess, onSaveError);
            } else {
                TypeEthnicity.save(vm.typeEthnicity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeEthnicityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
