(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TubeDetailController', TubeDetailController);

    TubeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Tube', 'Compendium'];

    function TubeDetailController($scope, $rootScope, $stateParams, previousState, entity, Tube, Compendium) {
        var vm = this;

        vm.tube = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:tubeUpdate', function(event, result) {
            vm.tube = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
