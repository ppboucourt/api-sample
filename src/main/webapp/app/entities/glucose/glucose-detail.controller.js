(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GlucoseDetailController', GlucoseDetailController);

    GlucoseDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Glucose', 'Chart', 'GlucoseIntervention'];

    function GlucoseDetailController($scope, $rootScope, $stateParams, previousState, entity, Glucose, Chart, GlucoseIntervention) {
        var vm = this;

        vm.glucose = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:glucoseUpdate', function(event, result) {
            vm.glucose = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
