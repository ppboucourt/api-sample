(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupTypeDeleteController',GroupTypeDeleteController);

    GroupTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupType'];

    function GroupTypeDeleteController($uibModalInstance, entity, GroupType) {
        var vm = this;

        vm.groupType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
