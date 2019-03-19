(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Icd10DeleteController',Icd10DeleteController);

    Icd10DeleteController.$inject = ['$uibModalInstance', 'entity', 'Icd10'];

    function Icd10DeleteController($uibModalInstance, entity, Icd10) {
        var vm = this;

        vm.icd10 = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Icd10.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
