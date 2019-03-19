(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationContentDeleteController',EvaluationContentDeleteController);

    EvaluationContentDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvaluationContent'];

    function EvaluationContentDeleteController($uibModalInstance, entity, EvaluationContent) {
        var vm = this;

        vm.evaluationContent = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvaluationContent.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
