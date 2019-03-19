(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeSpecialityDeleteController',TypeSpecialityDeleteController);

    TypeSpecialityDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeSpeciality'];

    function TypeSpecialityDeleteController($uibModalInstance, entity, TypeSpeciality) {
        var vm = this;

        vm.typeSpecialitys = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeSpeciality.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
