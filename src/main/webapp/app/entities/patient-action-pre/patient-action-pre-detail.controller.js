(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionPreDetailController', PatientActionPreDetailController);

    PatientActionPreDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientActionPre', 'Icd10', 'PatientActionTake', 'LabCompendium', 'PatientAction', 'ActionFrequency'];

    function PatientActionPreDetailController($scope, $rootScope, $stateParams, previousState, entity, PatientActionPre, Icd10, PatientActionTake, LabCompendium, PatientAction, ActionFrequency) {
        var vm = this;

        vm.patientActionPre = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:patientActionPreUpdate', function(event, result) {
            vm.patientActionPre = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
