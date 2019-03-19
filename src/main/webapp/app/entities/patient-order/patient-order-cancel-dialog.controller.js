(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderCancelController', PatientOrderCancelController);

    PatientOrderCancelController.$inject = ['$uibModalInstance', 'patientOrder', 'PatientOrder', 'Via', 'Employee'];

    function PatientOrderCancelController($uibModalInstance, patientOrder, PatientOrder, Via, Employee) {
        var vm = this;

        vm.patientOrder = patientOrder;
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
            vm.patientOrder.discountById = vm.patientOrder.discountBy.id;
            vm.patientOrder.discountCause=  vm.patientOrder.discountCause;

            PatientOrder.update(vm.patientOrder, function (data) {
                PatientOrder.cancel({id: data.id},
                    function () {
                        $uibModalInstance.close(true);
                    }
                );
            });


        }
    }
})();
