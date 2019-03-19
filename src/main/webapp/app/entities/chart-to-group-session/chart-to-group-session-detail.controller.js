(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToGroupSessionDetailController', ChartToGroupSessionDetailController);

    ChartToGroupSessionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToGroupSession', 'Chart', 'GroupSession'];

    function ChartToGroupSessionDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToGroupSession, Chart, GroupSession) {
        var vm = this;

        vm.chartToGroupSession = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToGroupSessionUpdate', function(event, result) {
            vm.chartToGroupSession = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
