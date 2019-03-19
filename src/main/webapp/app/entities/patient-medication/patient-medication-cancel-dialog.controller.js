(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationCancelController', PatientMedicationCancelController);

    PatientMedicationCancelController.$inject = ['$uibModalInstance', 'patientMedication', 'PatientMedication', 'Via', 'Employee'];

    function PatientMedicationCancelController($uibModalInstance, patientMedication, PatientMedication, Via, Employee) {
        var vm = this;

        vm.patientMedication = patientMedication;

        vm.Via = Via;
        vm.Employee = Employee;

        vm.physicians= Employee.employeesCorpPhysician();
        vm.vias = Via.query();

        vm.clear = clear;
        vm.confirmCancel = confirmCancel;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmCancel(id) {
            vm.saving = true;
            vm.patientMedication.discountById = vm.patientMedication.discountBy.id;
            vm.patientMedication.discountViaId = vm.patientMedication.discountVia.id;

            PatientMedication.update(vm.patientMedication, function (data) {
                PatientMedication.cancel({id: data.id},
                    function () {
                        $uibModalInstance.close(true);
                    }
                );
            });


        }
    }
})();
