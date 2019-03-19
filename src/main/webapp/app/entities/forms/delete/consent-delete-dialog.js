/**
 * Created by PpTMUnited on 5/5/2017.
 */
(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ConsentDeleteController',ConsentDeleteController);

    ConsentDeleteController.$inject = [ '$uibModalInstance', 'entity', 'ChartToForm' ];

    function ConsentDeleteController( $uibModalInstance, entity, ChartToForm ) {
        var vm = this;

        vm.chartToForm = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss();
        }

        function confirmDelete (id) {
            ChartToForm.delete({id: id},
                function () {
                    $uibModalInstance.dismiss(id);
                });
        }

    }
})();
