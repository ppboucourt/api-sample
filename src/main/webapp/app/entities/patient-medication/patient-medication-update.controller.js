(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationUpdateController', PatientMedicationUpdateController);

    PatientMedicationUpdateController.$inject = ['$state', 'patientMedication', 'Via', 'Employee', 'PatientMedication'];

    function PatientMedicationUpdateController($state, patientMedication,  Via, Employee, PatientMedication) {
        var vm = this;

        vm.patientMedication = patientMedication;
        vm.canSign = false;
        vm.canFinish = false;
        vm.canCancel = false;

        // if (vm.patientOrder.ordStatus == 'SCHEDULED') {
        //     vm.canFinish = CoreService.getCurrentEmployee().id == vm.patientOrder.signedBy.id;
        //     vm.canCancel = CoreService.getCurrentEmployee().id == vm.patientOrder.signedBy.id;
        //
        //     for (var i = 0; i < patientOrder.patientOrderTests.length && vm.canCancel; i++) {
        //         for (var j = 0; j < patientOrder.patientOrderTests[i].patientOrderItems.length && vm.canCancel; j++) {
        //             vm.canCancel = vm.canCancel && !patientOrder.patientOrderTests[i].patientOrderItems[j].collected;
        //         }
        //     }
        //
        //     vm.canFinish = vm.canFinish && !vm.canCancel;
        //
        //     if (!vm.patientOrder.signed && vm.patientOrder.signedBy.id == CoreService.getCurrentEmployee().id) {
        //         vm.canSign = true;
        //     } else {
        //         vm.canSign = false;
        //     }
        // } else {
        //     $state.go("patient-order-current", {orderType: $stateParams.orderType});
        // }

        vm.form = {};
        vm.save = save;
        vm.vias = Via.query();
        // vm.employees = Employee.employeeCorpPhysicians();
        vm.employees = Employee.query();

        function save() {
            vm.isSaving = true;
            PatientMedication.update(vm.patientMedication, onSaveSuccess, onSaveError);
        }

        function onSaveSuccess(result) {
            vm.isSaving = false;
            $state.go("patient-orders");
        }

        function onSaveError() {
            vm.isSaving = false;
        }

        vm.signIn = signIn;

        function signIn() {
            if (vm.canSign) {
                vm.patientMedication.signed = true;
            }

            PatientMedication.update(vm.patientMedication, onSaveSuccess, onSaveError);
        }
    }
})();
