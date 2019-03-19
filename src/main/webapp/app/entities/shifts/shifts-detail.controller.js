(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ShiftsDetailController', ShiftsDetailController);

    ShiftsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Shifts',
        'Employee', '$state', 'CoreService', 'File'];

    function ShiftsDetailController($scope, $rootScope, $stateParams, previousState, entity, Shifts, Employee, $state,
                                    CoreService, File) {
        var vm = this;
        vm.changeEditingState = changeEditingState;
        vm.save = save;
        vm.form ={};
        vm.shifts = entity;
        vm.previousState = previousState.name;
        vm.fromDetail = $stateParams.fromDetail;
        File.byOwner({id: vm.shifts.id}, function (result) {
            vm.files = result;
            // debugger
        });

        Shifts.currentPatients({id: CoreService.getCurrentFacility().id}).$promise.then(function (data) {
                vm.currentPatients = data;
            }, function (reason) {
                console.log('Failed getting employee: ' + reason);
            }
        );


        var unsubscribe = $rootScope.$on('bluebookApp:shiftsUpdate', function(event, result) {
            vm.shifts = result;
        });
        $scope.$on('$destroy', unsubscribe);

        function changeEditingState() {
            vm.editing = !vm.editing;
        }
        function save () {
            vm.isSaving = true;
            Shifts.update(vm.shifts, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:labProfileUpdate', result);
            vm.isSaving = false;
            $state.go('shifts', null, { reload: 'shifts' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
