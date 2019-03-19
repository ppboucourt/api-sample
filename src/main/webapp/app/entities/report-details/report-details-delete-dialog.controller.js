(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportDetailsDeleteController',ReportDetailsDeleteController);

    ReportDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'ReportDetails'];

    function ReportDetailsDeleteController($uibModalInstance, entity, ReportDetails) {
        var vm = this;

        vm.reportDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ReportDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
