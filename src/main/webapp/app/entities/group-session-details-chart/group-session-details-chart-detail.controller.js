(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupSessionDetailsChartDetailController', GroupSessionDetailsChartDetailController);

    GroupSessionDetailsChartDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity',
        'GroupSessionDetailsChart', 'GroupSessionDetails'];

    function GroupSessionDetailsChartDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupSessionDetailsChart, GroupSessionDetails) {
        var vm = this;

        vm.groupSessionDetailsChart = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:groupSessionDetailsChartUpdate', function(event, result) {
            vm.groupSessionDetailsChart = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
