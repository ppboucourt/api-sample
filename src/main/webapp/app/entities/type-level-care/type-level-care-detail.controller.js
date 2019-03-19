(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeLevelCareDetailController', TypeLevelCareDetailController);

    TypeLevelCareDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeLevelCare', 'Facility'];

    function TypeLevelCareDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeLevelCare, Facility) {
        var vm = this;

        vm.typeLevelCare = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeLevelCareUpdate', function(event, result) {
            vm.typeLevelCare = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
