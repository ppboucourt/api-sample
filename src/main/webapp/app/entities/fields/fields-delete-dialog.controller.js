(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FieldsDeleteController',FieldsDeleteController);

    FieldsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Fields'];

    function FieldsDeleteController($uibModalInstance, entity, Fields) {
        var vm = this;

        vm.fields = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Fields.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
