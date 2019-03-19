(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEvaluationDeleteController',TypeEvaluationDeleteController);

    TypeEvaluationDeleteController.$inject = ['$uibModalInstance', 'entity', 'TypeEvaluation'];

    function TypeEvaluationDeleteController($uibModalInstance, entity, TypeEvaluation) {
        var vm = this;

        vm.typeEvaluation = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TypeEvaluation.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
