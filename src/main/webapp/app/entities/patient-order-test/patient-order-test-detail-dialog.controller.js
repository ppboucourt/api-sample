(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderTestDetailDialogController', PatientOrderTestDetailDialogController);

    PatientOrderTestDetailDialogController.$inject = [  'patientOrderTest', '$uibModalInstance', 'PatientOrder','PatientOrderItem'];

    function PatientOrderTestDetailDialogController ( patientOrderTest, $uibModalInstance, PatientOrder, PatientOrderItem) {
        var vm = this;

       // vm.patientOrder = patientOrder;
        vm.patientOrderTest = patientOrderTest;
       // vm.canCancel = canCancel;


        vm.patientOrderItems = PatientOrder.getPatientOrderTestItems({id: patientOrderTest.id});

        vm.form = {};

        vm.clear = function () {
            $uibModalInstance.dismiss('cancel');
        }

        vm.accept = function () {
            $uibModalInstance.close();
        }

        vm.cancelOrderTestItem = function (item) {
            if (!item.canceled) {
                PatientOrderItem.cancel({id: item.id}, function () {
                }, function () {
                    $state.reload("patient-order-update", {oid: vm.patientOrder.id});
                });
            }
        }
    }
})();
