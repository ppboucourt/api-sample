(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeRaceDeleteController',TypeRaceDeleteController);

    TypeRaceDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeRace'];

    function TypeRaceDeleteController($uibModalInstance, entity, TypeRace) {
        var vm = this;

        vm.typeRace = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeRace.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
