(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeRaceDialogController', TypeRaceDialogController);

    TypeRaceDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeRace'];

    function TypeRaceDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeRace) {
        var vm = this;

        vm.typeRace = entity;
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
            if (vm.typeRace.id !== null) {
                TypeRace.update(vm.typeRace, onSaveSuccess, onSaveError);
            } else {
                TypeRace.save(vm.typeRace, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeRaceUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
