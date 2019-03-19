(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeTreatmentPlanStatusesDetailController', TypeTreatmentPlanStatusesDetailController);

    TypeTreatmentPlanStatusesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeTreatmentPlanStatuses'];

    function TypeTreatmentPlanStatusesDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeTreatmentPlanStatuses) {
        var vm = this;

        vm.typeTreatmentPlanStatuses = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeTreatmentPlanStatusesUpdate', function(event, result) {
            vm.typeTreatmentPlanStatuses = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
