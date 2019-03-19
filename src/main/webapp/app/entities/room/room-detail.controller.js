(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('RoomDetailController', RoomDetailController);

    RoomDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Room', 'Building', 'Bed', 'Facility'];

    function RoomDetailController($scope, $rootScope, $stateParams, previousState, entity, Room, Building, Bed, Facility) {
        var vm = this;

        vm.room = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:roomUpdate', function(event, result) {
            vm.room = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
