(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('RoutesDialogController', RoutesDialogController);

    RoutesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Routes'];

    function RoutesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Routes) {
        var vm = this;

        vm.routes = entity;
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
            if (vm.routes.id !== null) {
                Routes.update(vm.routes, onSaveSuccess, onSaveError);
            } else {
                Routes.save(vm.routes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:routesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
