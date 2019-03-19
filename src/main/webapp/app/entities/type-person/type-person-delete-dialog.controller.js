(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePersonDeleteController',TypePersonDeleteController);

    TypePersonDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypePerson'];

    function TypePersonDeleteController($uibModalInstance, entity, TypePerson) {
        var vm = this;

        vm.typePerson = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypePerson.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
