(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientMedicationTakeDetailController', PatientMedicationTakeDetailController);

    PatientMedicationTakeDetailController.$inject = ['CoreService', '$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientMedicationTake', 'PatientMedicationPres'];

    function PatientMedicationTakeDetailController(CoreService, $scope, $rootScope, $stateParams, previousState, entity, PatientMedicationTake, PatientMedicationPres) {
        var vm = this;

        vm.patientMedicationTake = entity;
        vm.previousState = previousState.name;

        vm.coreService = CoreService;


        var unsubscribe = $rootScope.$on('bluebookApp:patientMedicationTakeUpdate', function(event, result) {
            vm.patientMedicationTake = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
