(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TreatmentHistoryDeleteController',TreatmentHistoryDeleteController);

    TreatmentHistoryDeleteController.$inject = ['$uibModalInstance', 'entity', 'TreatmentHistory'];

    function TreatmentHistoryDeleteController($uibModalInstance, entity, TreatmentHistory) {
        var vm = this;

        vm.treatmentHistory = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TreatmentHistory.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
