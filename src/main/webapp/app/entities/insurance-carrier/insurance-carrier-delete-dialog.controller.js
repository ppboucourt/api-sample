(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceCarrierDeleteController',InsuranceCarrierDeleteController);

    InsuranceCarrierDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceCarrier'];

    function InsuranceCarrierDeleteController($uibModalInstance, entity, InsuranceCarrier) {
        var vm = this;

        vm.insuranceCarrier = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceCarrier.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
