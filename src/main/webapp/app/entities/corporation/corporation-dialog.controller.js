(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CorporationDialogController', CorporationDialogController);

    CorporationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Corporation'];

    function CorporationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Corporation) {
        var vm = this;

        vm.corporation = entity;
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
            if (vm.corporation.id !== null) {
                Corporation.update(vm.corporation, onSaveSuccess, onSaveError);
            } else {
                Corporation.save(vm.corporation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:corporationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
