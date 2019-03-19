(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseInterventionDetailController', GlucoseInterventionDetailController);

    GlucoseInterventionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GlucoseIntervention', 'Glucose'];

    function GlucoseInterventionDetailController($scope, $rootScope, $stateParams, previousState, entity, GlucoseIntervention, Glucose) {
        var vm = this;

        vm.glucoseIntervention = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:glucoseInterventionUpdate', function(event, result) {
            vm.glucoseIntervention = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
