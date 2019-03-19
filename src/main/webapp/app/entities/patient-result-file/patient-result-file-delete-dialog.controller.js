(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientResultFileDeleteController',PatientResultFileDeleteController);

    PatientResultFileDeleteController.$inject = ['$uibModalInstance', 'entity', 'PatientResultFile'];

    function PatientResultFileDeleteController($uibModalInstance, entity, PatientResultFile) {
        var vm = this;

        vm.patientResultFile = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PatientResultFile.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
