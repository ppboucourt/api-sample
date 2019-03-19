(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ContactsFacilityDetailController', ContactsFacilityDetailController);

    ContactsFacilityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'ContactsFacility', 'Facility', 'TypeContactFacilityType', 'CountryState'];

    function ContactsFacilityDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, ContactsFacility, Facility, TypeContactFacilityType, CountryState) {
        var vm = this;

        vm.contactsFacility = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('bluebookApp:contactsFacilityUpdate', function(event, result) {
            vm.contactsFacility = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
