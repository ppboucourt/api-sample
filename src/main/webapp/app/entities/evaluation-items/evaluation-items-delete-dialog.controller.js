(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationItemsDeleteController',EvaluationItemsDeleteController);

    EvaluationItemsDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvaluationItems'];

    function EvaluationItemsDeleteController($uibModalInstance, entity, EvaluationItems) {
        var vm = this;

        vm.evaluationItems = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvaluationItems.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
