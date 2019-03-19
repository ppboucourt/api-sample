(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeRaceDetailController', TypeRaceDetailController);

    TypeRaceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeRace'];

    function TypeRaceDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeRace) {
        var vm = this;

        vm.typeRace = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeRaceUpdate', function(event, result) {
            vm.typeRace = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
