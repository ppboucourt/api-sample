(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DietDetailController', DietDetailController);

    DietDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Diet', 'Chart', 'TypeFoodDiets'];

    function DietDetailController($scope, $rootScope, $stateParams, previousState, entity, Diet, Chart, TypeFoodDiets) {
        var vm = this;

        vm.diet = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:dietUpdate', function(event, result) {
            vm.diet = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
