(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationFrequencyDetailController', MedicationFrequencyDetailController);

    MedicationFrequencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'MedicationFrequency', 'PatientMedicationPrescription'];

    function MedicationFrequencyDetailController($scope, $rootScope, $stateParams, previousState, entity, MedicationFrequency, PatientMedicationPrescription) {
        var vm = this;

        vm.medicationFrequency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:medicationFrequencyUpdate', function(event, result) {
            vm.medicationFrequency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
