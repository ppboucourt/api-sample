(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationContentDialogController', EvaluationContentDialogController);

    EvaluationContentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluationContent'];

    function EvaluationContentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluationContent) {
        var vm = this;

        vm.evaluationContent = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluationContent.id !== null) {
                EvaluationContent.update(vm.evaluationContent, onSaveSuccess, onSaveError);
            } else {
                EvaluationContent.save(vm.evaluationContent, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:evaluationContentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
