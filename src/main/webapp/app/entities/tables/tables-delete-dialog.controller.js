(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TablesDeleteController',TablesDeleteController);

    TablesDeleteController.$inject = ['$uibModalInstance', 'entity', 'Tables'];

    function TablesDeleteController($uibModalInstance, entity, Tables) {
        var vm = this;

        vm.tables = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Tables.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
