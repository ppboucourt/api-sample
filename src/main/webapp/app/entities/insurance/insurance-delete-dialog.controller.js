(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceDeleteController',InsuranceDeleteController);

    InsuranceDeleteController.$inject = ['$uibModalInstance', 'entity', 'Insurance'];

    function InsuranceDeleteController($uibModalInstance, entity, Insurance) {
        var vm = this;

        vm.insurance = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Insurance.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
