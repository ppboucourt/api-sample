(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CountryStateDetailController', CountryStateDetailController);

    CountryStateDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CountryState'];

    function CountryStateDetailController($scope, $rootScope, $stateParams, previousState, entity, CountryState) {
        var vm = this;

        vm.countryState = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:countryStateUpdate', function(event, result) {
            vm.countryState = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
