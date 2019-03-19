(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDeleteController',GroupSessionDeleteController);

    GroupSessionDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupSession'];

    function GroupSessionDeleteController($uibModalInstance, entity, GroupSession) {
        var vm = this;

        vm.groupSession = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupSession.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
