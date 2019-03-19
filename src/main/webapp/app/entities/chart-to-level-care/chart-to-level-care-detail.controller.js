(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToLevelCareDetailController', ChartToLevelCareDetailController);

    ChartToLevelCareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToLevelCare', 'Chart', 'TypeLevelCare'];

    function ChartToLevelCareDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToLevelCare, Chart, TypeLevelCare) {
        var vm = this;

        vm.chartToLevelCare = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToLevelCareUpdate', function(event, result) {
            vm.chartToLevelCare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
