(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CountryStateDialogController', CountryStateDialogController);

    CountryStateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CountryState'];

    function CountryStateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CountryState) {
        var vm = this;

        vm.countryState = entity;
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
            if (vm.countryState.id !== null) {
                CountryState.update(vm.countryState, onSaveSuccess, onSaveError);
            } else {
                CountryState.save(vm.countryState, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:countryStateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
