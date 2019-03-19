(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionCancelController', PatientActionCancelController);

    PatientActionCancelController.$inject = ['$uibModalInstance', 'patientAction', 'PatientAction', 'Via', 'Employee'];

    function PatientActionCancelController($uibModalInstance, patientAction, PatientAction, Via, Employee) {
        var vm = this;

        vm.patientAction = patientAction;

        vm.Via = Via;
        vm.Employee = Employee;

        vm.physicians= Employee.employeesCorpPhysician();
        vm.vias = Via.query();

        vm.clear = clear;
        vm.confirmCancel = confirmCancel;

        function clear() {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmCancel() {
            vm.saving = true;
            vm.patientAction.discountById = vm.patientAction.discountBy.id;
            vm.patientAction.discountViaId = vm.patientAction.discountVia.id;

            PatientAction.update(vm.patientAction, function (data) {
                PatientAction.cancel({id: data.id},
                    function () {
                        $uibModalInstance.close(true);
                    }
                );
            });
        }
    }
})();
