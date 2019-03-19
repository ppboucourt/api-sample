(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('BedDetailController', BedDetailController);

    BedDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Bed', 'Room', 'Facility', 'Chart'];

    function BedDetailController($scope, $rootScope, $stateParams, previousState, entity, Bed, Room, Facility, Chart) {
        var vm = this;

        vm.bed = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:bedUpdate', function(event, result) {
            vm.bed = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
