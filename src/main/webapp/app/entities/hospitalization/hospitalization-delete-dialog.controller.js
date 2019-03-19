(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('HospitalizationDeleteController',HospitalizationDeleteController);

    HospitalizationDeleteController.$inject = ['$uibModalInstance', 'entity', 'Hospitalization'];

    function HospitalizationDeleteController($uibModalInstance, entity, Hospitalization) {
        var vm = this;

        vm.hospitalization = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Hospitalization.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
