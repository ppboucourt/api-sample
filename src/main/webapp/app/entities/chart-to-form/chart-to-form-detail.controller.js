(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToFormDetailController', ChartToFormDetailController);

    ChartToFormDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToForm', 'Chart', 'Forms'];

    function ChartToFormDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToForm, Chart, Forms) {
        var vm = this;

        vm.chartToForm = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToFormUpdate', function(event, result) {
            vm.chartToForm = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
