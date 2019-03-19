(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionDeleteController',PatientActionDeleteController);

    PatientActionDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientAction'];

    function PatientActionDeleteController($uibModalInstance, entity, PatientAction) {
        var vm = this;

        vm.patientAction = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientAction.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
