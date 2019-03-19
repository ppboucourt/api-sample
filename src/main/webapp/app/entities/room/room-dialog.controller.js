(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('RoomDialogController', RoomDialogController);

    RoomDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Room', 'Building', 'Bed',
        'Facility', 'CoreService', 'GUIUtils'];

    function RoomDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Room, Building, Bed,
                                   Facility, CoreService, GUIUtils) {
        var vm = this;

        vm.room = entity;
        vm.clear = clear;
        vm.save = save;
        vm.buildings = Building.query();
        vm.beds = Bed.query();
        vm.facilities = Facility.query();
        vm.gender = GUIUtils.getGender();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            vm.room.facility = CoreService.getCurrentFacility();
            if (vm.room.id !== null) {
                Room.update(vm.room, onSaveSuccess, onSaveError);
            } else {
                vm.room.status = 'ACT';
                Room.save(vm.room, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:roomUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
