(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseInterventionDialogController', GlucoseInterventionDialogController);

    GlucoseInterventionDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'GlucoseIntervention', 'Glucose'];

    function GlucoseInterventionDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, GlucoseIntervention, Glucose) {
        var vm = this;

        vm.glucoseIntervention = entity;
        vm.clear = clear;
        vm.save = save;
        vm.glucoses = Glucose.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.glucoseIntervention.id !== null) {
                GlucoseIntervention.update(vm.glucoseIntervention, onSaveSuccess, onSaveError);
            } else {
                GlucoseIntervention.save(vm.glucoseIntervention, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('bluebookApp:glucoseInterventionUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
