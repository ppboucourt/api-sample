(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEmployeeDeleteController',TypeEmployeeDeleteController);

    TypeEmployeeDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeEmployee'];

    function TypeEmployeeDeleteController($uibModalInstance, entity, TypeEmployee) {
        var vm = this;

        vm.typeEmployee = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeEmployee.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
