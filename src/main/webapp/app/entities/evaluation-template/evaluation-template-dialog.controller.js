(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationTemplateDialogController', EvaluationTemplateDialogController);

    EvaluationTemplateDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluationTemplate', 'TypePatientProcess', 'TypeEvaluation', 'EvaluationContent'];

    function EvaluationTemplateDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluationTemplate, TypePatientProcess, TypeEvaluation, EvaluationContent) {
        var vm = this;

        vm.evaluationTemplate = entity;
        vm.clear = clear;
        vm.save = save;
        vm.typepatientprocesses = TypePatientProcess.query();
        vm.typeevaluations = TypeEvaluation.query();
        vm.evaluationcontents = EvaluationContent.query();
        vm.form = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluationTemplate.id !== null) {
                EvaluationTemplate.update(vm.evaluationTemplate, onSaveSuccess, onSaveError);
            } else {
                EvaluationTemplate.save(vm.evaluationTemplate, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:evaluationTemplateUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
