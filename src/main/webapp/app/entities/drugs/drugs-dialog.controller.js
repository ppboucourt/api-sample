(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DrugsDialogController', DrugsDialogController);

    DrugsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Drugs', 'Chart'];

    function DrugsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Drugs, Chart) {
        var vm = this;

        vm.drugs = entity;
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
            if (vm.drugs.id !== null) {
                Drugs.update(vm.drugs, onSaveSuccess, onSaveError);
            } else {
                Drugs.save(vm.drugs, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:drugsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
