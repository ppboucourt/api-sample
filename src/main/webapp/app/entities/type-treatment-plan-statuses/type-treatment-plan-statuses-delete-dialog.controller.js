(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeTreatmentPlanStatusesDeleteController',TypeTreatmentPlanStatusesDeleteController);

    TypeTreatmentPlanStatusesDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeTreatmentPlanStatuses'];

    function TypeTreatmentPlanStatusesDeleteController($uibModalInstance, entity, TypeTreatmentPlanStatuses) {
        var vm = this;

        vm.typeTreatmentPlanStatuses = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeTreatmentPlanStatuses.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
