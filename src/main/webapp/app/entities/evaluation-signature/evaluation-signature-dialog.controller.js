(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationSignatureDialogController', EvaluationSignatureDialogController);

    EvaluationSignatureDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluationSignature', 'Evaluation', 'Employee', 'Signature'];

    function EvaluationSignatureDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluationSignature, Evaluation, Employee, Signature) {
        var vm = this;

        vm.evaluationSignature = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluations = Evaluation.query();
        vm.employees = Employee.query();
        vm.signatures = Signature.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluationSignature.id !== null) {
                EvaluationSignature.update(vm.evaluationSignature, onSaveSuccess, onSaveError);
            } else {
                EvaluationSignature.save(vm.evaluationSignature, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:evaluationSignatureUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
