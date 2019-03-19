(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePreAdmissionStatusDialogController', TypePreAdmissionStatusDialogController);

    TypePreAdmissionStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePreAdmissionStatus'];

    function TypePreAdmissionStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePreAdmissionStatus) {
        var vm = this;

        vm.typePreAdmissionStatus = entity;
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
            if (vm.typePreAdmissionStatus.id !== null) {
                TypePreAdmissionStatus.update(vm.typePreAdmissionStatus, onSaveSuccess, onSaveError);
            } else {
                TypePreAdmissionStatus.save(vm.typePreAdmissionStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePreAdmissionStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
