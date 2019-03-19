(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDischargeTypeDeleteController',TypeDischargeTypeDeleteController);

    TypeDischargeTypeDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeDischargeType'];

    function TypeDischargeTypeDeleteController($uibModalInstance, entity, TypeDischargeType) {
        var vm = this;

        vm.typeDischargeType = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeDischargeType.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
