(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('WeightDetailController', WeightDetailController);

    WeightDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Weight', 'Chart'];

    function WeightDetailController($scope, $rootScope, $stateParams, previousState, entity, Weight, Chart) {
        var vm = this;

        vm.weight = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:weightUpdate', function(event, result) {
            vm.weight = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
