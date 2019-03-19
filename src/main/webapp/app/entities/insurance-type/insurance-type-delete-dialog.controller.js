(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceTypeDeleteController',InsuranceTypeDeleteController);

    InsuranceTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceType'];

    function InsuranceTypeDeleteController($uibModalInstance, entity, InsuranceType) {
        var vm = this;

        vm.insuranceType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
