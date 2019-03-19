(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CountryStateDeleteController',CountryStateDeleteController);

    CountryStateDeleteController.$inject = ['$uibModalInstance', 'entity', 'CountryState'];

    function CountryStateDeleteController($uibModalInstance, entity, CountryState) {
        var vm = this;

        vm.countryState = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CountryState.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
