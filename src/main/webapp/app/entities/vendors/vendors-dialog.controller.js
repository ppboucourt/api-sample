(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VendorsDialogController', VendorsDialogController);

    VendorsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Vendors'];

    function VendorsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Vendors) {
        var vm = this;

        vm.vendors = entity;
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
            if (vm.vendors.id !== null) {
                Vendors.update(vm.vendors, onSaveSuccess, onSaveError);
            } else {
                Vendors.save(vm.vendors, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:vendorsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
