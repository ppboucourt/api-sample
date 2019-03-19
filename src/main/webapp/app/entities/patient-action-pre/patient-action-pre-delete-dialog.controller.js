(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionPreDeleteController',PatientActionPreDeleteController);

    PatientActionPreDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientActionPre'];

    function PatientActionPreDeleteController($uibModalInstance, entity, PatientActionPre) {
        var vm = this;

        vm.patientActionPre = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientActionPre.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
