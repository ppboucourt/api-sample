(function () {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderViewController', PatientOrderViewController);

    PatientOrderViewController.$inject = ['$state', 'patientOrder', 'entity'];

    function PatientOrderViewController($state, patientOrder, entity) {
        var vm = this;

        vm.patientOrder = patientOrder;
        vm.patient = entity;

        if (vm.patientOrder.ordStatus == 'SCHEDULED') {
            $state.go("patient-order-update", {id: vm.patient.id, oid: vm.patientOrder.id});
        }
    }
})();
