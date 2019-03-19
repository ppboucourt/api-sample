(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePageDeleteController',TypePageDeleteController);

    TypePageDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePage'];

    function TypePageDeleteController($uibModalInstance, entity, TypePage) {
        var vm = this;

        vm.typePage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
