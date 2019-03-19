(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionSignController', PatientActionSignController);

    PatientActionSignController.$inject = ['patientAction', '$uibModalInstance', 'CoreService', 'Employee'];

    function PatientActionSignController(patientAction, $uibModalInstance, CoreService, Employee) {
        var vm = this;

        vm.clear = clear;
        vm.confirmSign = confirmSign;
        vm.employee = CoreService.getCurrentEmployee();
        vm.patientAction = patientAction;


        vm.title ='Sign Action';

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSign() {
            vm.isSaving = true;

            Employee.employeeIsValidPin({id: vm.employee.id, pin: vm.employee.pin}, function (result) {

                if (result && result.value) {

                    if (result.value) {
                        $uibModalInstance.close(vm.patientAction);
                    }
                }else{
                    alert('Incorrect Pin.');
                    vm.isSaving = false;

                }

            }, function (error) {
                console.log(error);
                vm.isSaving = false;
            });


        }
    }
})();
