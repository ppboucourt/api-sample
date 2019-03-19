(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEvaluationDialogController', TypeEvaluationDialogController);

    TypeEvaluationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeEvaluation'];

    function TypeEvaluationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeEvaluation) {
        var vm = this;

        vm.typeEvaluation = entity;
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
            if (vm.typeEvaluation.id !== null) {
                TypeEvaluation.update(vm.typeEvaluation, onSaveSuccess, onSaveError);
            } else {
                TypeEvaluation.save(vm.typeEvaluation, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeEvaluationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
