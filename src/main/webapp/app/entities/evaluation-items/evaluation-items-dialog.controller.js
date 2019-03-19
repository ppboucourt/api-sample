(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EvaluationItemsDialogController', EvaluationItemsDialogController);

    EvaluationItemsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'EvaluationItems', 'Evaluation'];

    function EvaluationItemsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, EvaluationItems, Evaluation) {
        var vm = this;

        vm.evaluationItems = entity;
        vm.clear = clear;
        vm.save = save;
        vm.evaluations = Evaluation.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.evaluationItems.id !== null) {
                EvaluationItems.update(vm.evaluationItems, onSaveSuccess, onSaveError);
            } else {
                EvaluationItems.save(vm.evaluationItems, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:evaluationItemsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
