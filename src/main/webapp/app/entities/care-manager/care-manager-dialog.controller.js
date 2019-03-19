(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CareManagerDialogController', CareManagerDialogController);

    CareManagerDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CareManager', 'Chart'];

    function CareManagerDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CareManager, Chart) {
        var vm = this;

        vm.careManager = entity;
        vm.clear = clear;
        vm.save = save;
        vm.charts = Chart.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.careManager.id !== null) {
                CareManager.update(vm.careManager, onSaveSuccess, onSaveError);
            } else {
                CareManager.save(vm.careManager, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:careManagerUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
