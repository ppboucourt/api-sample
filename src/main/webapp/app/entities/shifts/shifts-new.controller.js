(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ShiftsNewController', ShiftsNewController);

    ShiftsNewController.$inject = ['$timeout', '$scope', '$stateParams', /*'$uibModalInstance',*/ 'entity', 'Shifts', 'Employee', 'CoreService', '$state'];

    function ShiftsNewController ($timeout, $scope, $stateParams, /*$uibModalInstance, */entity, Shifts, Employee, CoreService, $state) {
        var vm = this;

        vm.shifts = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();

        Shifts.currentPatients({id: CoreService.getCurrentFacility().id}).$promise.then(function (data) {
                vm.currentPatients = data;
            }, function (reason) {
                console.log('Failed getting employee: ' + reason);
            }
        );

        // Editor options.
        vm.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.shifts.id !== null) {
                vm.shifts.employee = CoreService.getCurrentEmployee();
                Shifts.update(vm.shifts, onSaveSuccess, onSaveError);
            } else {
                Shifts.save(vm.shifts, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:shiftsUpdate', result);
            // $uibModalInstance.close(result);
            vm.isSaving = false;
            $state.go('shifts', null, { reload: 'shifts' });
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
