(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationTemplateDeleteController',EvaluationTemplateDeleteController);

    EvaluationTemplateDeleteController.$inject = ['$uibModalInstance', 'entity', 'EvaluationTemplate'];

    function EvaluationTemplateDeleteController($uibModalInstance, entity, EvaluationTemplate) {
        var vm = this;

        vm.evaluationTemplate = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            EvaluationTemplate.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
