(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceLevelDeleteController',InsuranceLevelDeleteController);

    InsuranceLevelDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceLevel'];

    function InsuranceLevelDeleteController($uibModalInstance, entity, InsuranceLevel) {
        var vm = this;

        vm.insuranceLevel = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceLevel.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
