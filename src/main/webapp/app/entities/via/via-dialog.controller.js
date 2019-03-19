(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ViaDialogController', ViaDialogController);

    ViaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Via', 'PatientOrder'];

    function ViaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Via, PatientOrder) {
        var vm = this;

        vm.via = entity;
        vm.clear = clear;
        vm.save = save;
        vm.patientorders = PatientOrder.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.via.id !== null) {
                Via.update(vm.via, onSaveSuccess, onSaveError);
            } else {
                Via.save(vm.via, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:viaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
