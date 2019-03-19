(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEmployeeDialogController', TypeEmployeeDialogController);

    TypeEmployeeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeEmployee', 'Employee'];

    function TypeEmployeeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeEmployee, Employee) {
        var vm = this;

        vm.typeEmployee = entity;
        vm.clear = clear;
        vm.save = save;
        vm.employees = Employee.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.typeEmployee.id !== null) {
                TypeEmployee.update(vm.typeEmployee, onSaveSuccess, onSaveError);
            } else {
                TypeEmployee.save(vm.typeEmployee, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeEmployeeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
