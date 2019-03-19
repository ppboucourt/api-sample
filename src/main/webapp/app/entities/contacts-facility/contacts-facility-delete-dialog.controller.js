(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityDeleteController',ContactsFacilityDeleteController);

    ContactsFacilityDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContactsFacility'];

    function ContactsFacilityDeleteController($uibModalInstance, entity, ContactsFacility) {
        var vm = this;

        vm.contactsFacility = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContactsFacility.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
