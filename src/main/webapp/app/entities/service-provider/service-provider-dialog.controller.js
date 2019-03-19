(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ServiceProviderDialogController', ServiceProviderDialogController);

    ServiceProviderDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ServiceProvider'];

    function ServiceProviderDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ServiceProvider) {
        var vm = this;

        vm.serviceProvider = entity;
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
            if (vm.serviceProvider.id !== null) {
                ServiceProvider.update(vm.serviceProvider, onSaveSuccess, onSaveError);
            } else {
                ServiceProvider.save(vm.serviceProvider, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:serviceProviderUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
