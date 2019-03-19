(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeAdmissionStatusDialogController', TypeAdmissionStatusDialogController);

    TypeAdmissionStatusDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeAdmissionStatus', 'Chart', 'Facility'];

    function TypeAdmissionStatusDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeAdmissionStatus, Chart, Facility) {
        var vm = this;

        vm.typeAdmissionStatus = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();
        vm.facilities = Facility.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeAdmissionStatus.id !== null) {
                TypeAdmissionStatus.update(vm.typeAdmissionStatus, onSaveSuccess, onSaveError);
            } else {
                TypeAdmissionStatus.save(vm.typeAdmissionStatus, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeAdmissionStatusUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
