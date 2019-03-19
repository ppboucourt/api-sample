(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientActionTakeDetailController', PatientActionTakeDetailController);

    PatientActionTakeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientActionTake', 'PatientActionPres'];

    function PatientActionTakeDetailController($scope, $rootScope, $stateParams, previousState, entity, PatientActionTake, PatientActionPres) {
        var vm = this;

        vm.patientActionTake = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:patientActionTakeUpdate', function(event, result) {
            vm.patientActionTake = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
