(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProcessDialogController', TypePatientProcessDialogController);

    TypePatientProcessDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePatientProcess',
        'TypePage', 'CoreService'];

    function TypePatientProcessDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePatientProcess,
                                                 TypePage, CoreService) {
        var vm = this;

        vm.typePatientProcess = entity;
        vm.clear = clear;
        vm.save = save;
        vm.typepages = TypePage.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.typePatientProcess.facilityId = CoreService.getCurrentFacility().id;
            if (vm.typePatientProcess.id !== null) {
                TypePatientProcess.update(vm.typePatientProcess, onSaveSuccess, onSaveError);
            } else {
                TypePatientProcess.save(vm.typePatientProcess, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePatientProcessUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
