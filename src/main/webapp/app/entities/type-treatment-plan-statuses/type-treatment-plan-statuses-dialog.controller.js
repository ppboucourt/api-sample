(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeTreatmentPlanStatusesDialogController', TypeTreatmentPlanStatusesDialogController);

    TypeTreatmentPlanStatusesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TypeTreatmentPlanStatuses'];

    function TypeTreatmentPlanStatusesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TypeTreatmentPlanStatuses) {
        var vm = this;

        vm.typeTreatmentPlanStatuses = entity;
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
            if (vm.typeTreatmentPlanStatuses.id !== null) {
                TypeTreatmentPlanStatuses.update(vm.typeTreatmentPlanStatuses, onSaveSuccess, onSaveError);
            } else {
                TypeTreatmentPlanStatuses.save(vm.typeTreatmentPlanStatuses, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:typeTreatmentPlanStatusesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
