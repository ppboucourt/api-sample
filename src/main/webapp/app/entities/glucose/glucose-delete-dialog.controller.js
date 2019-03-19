(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseDeleteController',GlucoseDeleteController);

    GlucoseDeleteController.$inject = ['$uibModalInstance', 'entity', 'Glucose'];

    function GlucoseDeleteController($uibModalInstance, entity, Glucose) {
        var vm = this;

        vm.glucose = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Glucose.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
