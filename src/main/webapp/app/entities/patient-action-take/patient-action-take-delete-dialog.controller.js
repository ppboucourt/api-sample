(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeDeleteController',PatientActionTakeDeleteController);

    PatientActionTakeDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientActionTake'];

    function PatientActionTakeDeleteController($uibModalInstance, entity, PatientActionTake) {
        var vm = this;

        vm.patientActionTake = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientActionTake.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
