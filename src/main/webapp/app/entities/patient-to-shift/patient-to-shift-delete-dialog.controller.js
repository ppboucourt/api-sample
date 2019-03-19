(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientToShiftDeleteController',PatientToShiftDeleteController);

    PatientToShiftDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientToShift'];

    function PatientToShiftDeleteController($uibModalInstance, entity, PatientToShift) {
        var vm = this;

        vm.patientToShift = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientToShift.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
