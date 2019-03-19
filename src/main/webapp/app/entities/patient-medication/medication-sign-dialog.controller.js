(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationSignController', MedicationSignController);

    MedicationSignController.$inject = ['patientMedication', '$uibModalInstance', 'CoreService', 'Employee'];

    function MedicationSignController(patientMedication, $uibModalInstance, CoreService, Employee) {
        var vm = this;

        vm.clear = clear;
        vm.confirmSign = confirmSign;
        vm.employee = CoreService.getCurrentEmployee();
        vm.patientMedication = patientMedication;


        vm.title ='Sign Medication';

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmSign() {

            Employee.employeeIsValidPin({id: vm.employee.id, pin: vm.employee.pin}, function (result) {

                if (result && result.value) {

                    if (result.value) {
                        $uibModalInstance.close(vm.patientMedication);
                    }
                }

            }, function (error) {
                console.log(error);
            });


        }
    }
})();
