(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToActionDetailController', ChartToActionDetailController);

    ChartToActionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToAction', 'Chart', 'Actions'];

    function ChartToActionDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToAction, Chart, Actions) {
        var vm = this;

        vm.chartToAction = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToActionUpdate', function(event, result) {
            vm.chartToAction = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
