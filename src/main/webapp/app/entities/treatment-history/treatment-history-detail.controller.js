(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TreatmentHistoryDetailController', TreatmentHistoryDetailController);

    TreatmentHistoryDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TreatmentHistory', 'Chart'];

    function TreatmentHistoryDetailController($scope, $rootScope, $stateParams, previousState, entity, TreatmentHistory, Chart) {
        var vm = this;

        vm.treatmentHistory = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:treatmentHistoryUpdate', function(event, result) {
            vm.treatmentHistory = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
