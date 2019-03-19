(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('AllergiesDetailController', AllergiesDetailController);

    AllergiesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Allergies', 'Chart'];

    function AllergiesDetailController($scope, $rootScope, $stateParams, previousState, entity, Allergies, Chart) {
        var vm = this;

        vm.allergies = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:allergiesUpdate', function(event, result) {
            vm.allergies = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
