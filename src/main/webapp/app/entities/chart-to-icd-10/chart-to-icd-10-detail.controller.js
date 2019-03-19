(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToIcd10DetailController', ChartToIcd10DetailController);

    ChartToIcd10DetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToIcd10', 'Chart', 'Icd10'];

    function ChartToIcd10DetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToIcd10, Chart, Icd10) {
        var vm = this;

        vm.chartToIcd10 = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToIcd10Update', function(event, result) {
            vm.chartToIcd10 = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
