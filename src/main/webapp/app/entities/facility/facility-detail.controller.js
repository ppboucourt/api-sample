(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('FacilityDetailController', FacilityDetailController);

    FacilityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Facility', 'Building', 'ContactsFacility', 'Corporation'];

    function FacilityDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Facility, Building, ContactsFacility, Corporation) {
        var vm = this;

        vm.facility = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:facilityUpdate', function(event, result) {
            vm.facility = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
