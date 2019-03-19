(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Patient_propertiesDialogController', Patient_propertiesDialogController);

    Patient_propertiesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Patient_properties', 'Chart'];

    function Patient_propertiesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Patient_properties, Chart) {
        var vm = this;

        vm.patient_properties = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patient_properties.id !== null) {
                Patient_properties.update(vm.patient_properties, onSaveSuccess, onSaveError);
            } else {
                Patient_properties.save(vm.patient_properties, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:patient_propertiesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPicture = function ($file, patient_properties) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        patient_properties.picture = base64Data;
                        patient_properties.pictureContentType = $file.type;
                    });
                });
            }
        };

    }
})();
