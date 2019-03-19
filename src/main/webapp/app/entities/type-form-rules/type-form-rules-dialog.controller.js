(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFormRulesDialogController', TypeFormRulesDialogController);

    TypeFormRulesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeFormRules'];

    function TypeFormRulesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeFormRules) {
        var vm = this;

        vm.typeFormRules = entity;
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
            if (vm.typeFormRules.id !== null) {
                TypeFormRules.update(vm.typeFormRules, onSaveSuccess, onSaveError);
            } else {
                TypeFormRules.save(vm.typeFormRules, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeFormRulesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
