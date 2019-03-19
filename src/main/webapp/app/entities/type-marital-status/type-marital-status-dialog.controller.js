(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMaritalStatusDialogController', TypeMaritalStatusDialogController);

    TypeMaritalStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeMaritalStatus'];

    function TypeMaritalStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeMaritalStatus) {
        var vm = this;

        vm.typeMaritalStatus = entity;
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
            if (vm.typeMaritalStatus.id !== null) {
                TypeMaritalStatus.update(vm.typeMaritalStatus, onSaveSuccess, onSaveError);
            } else {
                TypeMaritalStatus.save(vm.typeMaritalStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeMaritalStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
