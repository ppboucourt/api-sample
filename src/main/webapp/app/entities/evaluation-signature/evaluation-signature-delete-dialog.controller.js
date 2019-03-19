(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationSignatureDeleteController',EvaluationSignatureDeleteController);

    EvaluationSignatureDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvaluationSignature'];

    function EvaluationSignatureDeleteController($uibModalInstance, entity, EvaluationSignature) {
        var vm = this;

        vm.evaluationSignature = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvaluationSignature.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
