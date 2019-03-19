(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProgramsDialogController', TypePatientProgramsDialogController);

    TypePatientProgramsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypePatientPrograms',
        'Chart', 'Facility', 'CoreService'];

    function TypePatientProgramsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypePatientPrograms,
                                                  Chart, Facility, CoreService) {
        var vm = this;

        vm.typePatientPrograms = entity;
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
            vm.typePatientPrograms.facility = CoreService.getCurrentFacility();
            if (vm.typePatientPrograms.id !== null) {
                TypePatientPrograms.update(vm.typePatientPrograms, onSaveSuccess, onSaveError);
            } else {
                TypePatientPrograms.save(vm.typePatientPrograms, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typePatientProgramsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
