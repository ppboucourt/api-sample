(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePersonDialogController', TypePersonDialogController);

    TypePersonDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePerson'];

    function TypePersonDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePerson) {
        var vm = this;

        vm.typePerson = entity;
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
            if (vm.typePerson.id !== null) {
                TypePerson.update(vm.typePerson, onSaveSuccess, onSaveError);
            } else {
                TypePerson.save(vm.typePerson, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePersonUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
