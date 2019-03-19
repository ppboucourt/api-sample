(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Patient_propertiesDeleteController',Patient_propertiesDeleteController);

    Patient_propertiesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Patient_properties'];

    function Patient_propertiesDeleteController($uibModalInstance, entity, Patient_properties) {
        var vm = this;

        vm.patient_properties = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Patient_properties.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
