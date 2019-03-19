(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BuildingDialogController', BuildingDialogController);

    BuildingDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Building', 'Facility',
        'Room', 'CoreService'];

    function BuildingDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Building, Facility,
                                       Room, CoreService ) {
        var vm = this;

        vm.building = entity;
        vm.clear = clear;
        vm.save = save;
        vm.facilities = Facility.query();
        vm.rooms = Room.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.building.id !== null) {
                Building.update(vm.building, onSaveSuccess, onSaveError);
            } else {
                vm.building.facility = CoreService.getCurrentFacility();
                vm.building.status = 'ACT';
                Building.save(vm.building, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:buildingUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
