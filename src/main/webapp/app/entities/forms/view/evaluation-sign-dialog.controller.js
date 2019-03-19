(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationSignController', EvaluationSignController);

    EvaluationSignController.$inject = ['$uibModalInstance', 'CoreService', 'Employee'];

    function EvaluationSignController($uibModalInstance, CoreService, Employee) {
        var vm = this;

        vm.clear = clear;
        vm.confirmSign = confirmSign;
        vm.employee = CoreService.getCurrentEmployee();
        vm.isSaving = false;


        vm.title ='Sign Evaluation';

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSign() {
            vm.isSaving = true;
            Employee.employeeIsValidPin({id: vm.employee.id, pin: vm.employee.pin}, function (result) {
                if (result && result.value) {
                      $uibModalInstance.close(true, result.value);
                }else{
                    alert("Pin wrong, please try again!");
                    vm.isSaving = false;
                }

            }, function (error) {
                console.log(error);
            });


        }
    }
})();
