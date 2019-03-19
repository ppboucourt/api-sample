(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsDeleteController',GroupSessionDetailsDeleteController);

    GroupSessionDetailsDeleteController.$inject = ['$uibModalInstance', 'entity', 'GroupSessionDetails'];

    function GroupSessionDetailsDeleteController($uibModalInstance, entity, GroupSessionDetails) {
        var vm = this;

        vm.groupSessionDetails = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            GroupSessionDetails.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
