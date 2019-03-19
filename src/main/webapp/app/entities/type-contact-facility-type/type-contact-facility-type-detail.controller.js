(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeContactFacilityTypeDetailController', TypeContactFacilityTypeDetailController);

    TypeContactFacilityTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeContactFacilityType'];

    function TypeContactFacilityTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeContactFacilityType) {
        var vm = this;

        vm.typeContactFacilityType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeContactFacilityTypeUpdate', function(event, result) {
            vm.typeContactFacilityType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
