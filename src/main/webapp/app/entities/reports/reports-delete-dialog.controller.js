(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportsDeleteController',ReportsDeleteController);

    ReportsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Reports'];

    function ReportsDeleteController($uibModalInstance, entity, Reports) {
        var vm = this;

        vm.reports = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Reports.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
