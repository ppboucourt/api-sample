(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BedDialogController', BedDialogController);

    BedDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Bed', 'Room',
        'Facility', 'Chart', 'CoreService'];

    function BedDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Bed, Room,
                                  Facility, Chart, CoreService) {
        var vm = this;

        vm.bed = entity;
        vm.clear = clear;
        vm.save = save;
        vm.rooms = Room.query();
        vm.facilities = Facility.query();
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.bed.id !== null) {
                Bed.update(vm.bed, onSaveSuccess, onSaveError);
            } else {
                vm.bed.status = 'ACT';
                vm.bed.facility = CoreService.getCurrentFacility();
                Bed.save(vm.bed, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:bedUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
