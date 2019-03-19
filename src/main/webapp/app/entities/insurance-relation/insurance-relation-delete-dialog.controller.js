(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceRelationDeleteController',InsuranceRelationDeleteController);

    InsuranceRelationDeleteController.$inject = ['$uibModalInstance', 'entity', 'InsuranceRelation'];

    function InsuranceRelationDeleteController($uibModalInstance, entity, InsuranceRelation) {
        var vm = this;

        vm.insuranceRelation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            InsuranceRelation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
