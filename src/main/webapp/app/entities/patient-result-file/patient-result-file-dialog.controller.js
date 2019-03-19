(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultFileDialogController', PatientResultFileDialogController);

    PatientResultFileDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PatientResultFile', 'PatientResult'];

    function PatientResultFileDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PatientResultFile, PatientResult) {
        var vm = this;

        vm.patientResultFile = entity;
        vm.clear = clear;
        vm.save = save;
        vm.patientresults = PatientResult.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patientResultFile.id !== null) {
                PatientResultFile.update(vm.patientResultFile, onSaveSuccess, onSaveError);
            } else {
                PatientResultFile.save(vm.patientResultFile, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patientResultFileUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
