(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TubeDialogController', TubeDialogController);

    TubeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Tube', 'Compendium'];

    function TubeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Tube, Compendium) {
        var vm = this;

        vm.tube = entity;
        vm.clear = clear;
        vm.save = save;
        vm.compendiums = Compendium.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tube.id !== null) {
                Tube.update(vm.tube, onSaveSuccess, onSaveError);
            } else {
                Tube.save(vm.tube, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:tubeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
