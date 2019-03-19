(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeFoodDietsDetailController', TypeFoodDietsDetailController);

    TypeFoodDietsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeFoodDiets'];

    function TypeFoodDietsDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeFoodDiets) {
        var vm = this;

        vm.typeFoodDiets = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeFoodDietsUpdate', function(event, result) {
            vm.typeFoodDiets = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
