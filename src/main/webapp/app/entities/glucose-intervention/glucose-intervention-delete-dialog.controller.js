(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseInterventionDeleteController',GlucoseInterventionDeleteController);

    GlucoseInterventionDeleteController.$inject = ['$uibModalInstance', 'entity', 'GlucoseIntervention'];

    function GlucoseInterventionDeleteController($uibModalInstance, entity, GlucoseIntervention) {
        var vm = this;

        vm.glucoseIntervention = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GlucoseIntervention.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
