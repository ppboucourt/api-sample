(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UrgentIssuesDeleteController',UrgentIssuesDeleteController);

    UrgentIssuesDeleteController.$inject = ['$uibModalInstance', 'entity', 'UrgentIssues'];

    function UrgentIssuesDeleteController($uibModalInstance, entity, UrgentIssues) {
        var vm = this;

        vm.urgentIssues = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            UrgentIssues.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
