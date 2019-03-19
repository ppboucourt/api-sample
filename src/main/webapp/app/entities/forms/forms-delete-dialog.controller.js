(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FormsDeleteController',FormsDeleteController);

    FormsDeleteController.$inject = ['$uibModalInstance', 'entity', 'Forms'];

    function FormsDeleteController($uibModalInstance, entity, Forms) {
        var vm = this;

        vm.forms = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss();
        }

        function confirmDelete (id) {
            Forms.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
