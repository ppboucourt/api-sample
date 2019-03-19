(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ChartToMedicationsDetailController', ChartToMedicationsDetailController);

    ChartToMedicationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ChartToMedications', 'Chart', 'Medications'];

    function ChartToMedicationsDetailController($scope, $rootScope, $stateParams, previousState, entity, ChartToMedications, Chart, Medications) {
        var vm = this;

        vm.chartToMedications = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:chartToMedicationsUpdate', function(event, result) {
            vm.chartToMedications = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
