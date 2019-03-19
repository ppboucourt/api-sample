(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ServiceProviderDetailController', ServiceProviderDetailController);

    ServiceProviderDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ServiceProvider'];

    function ServiceProviderDetailController($scope, $rootScope, $stateParams, previousState, entity, ServiceProvider) {
        var vm = this;

        vm.serviceProvider = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:serviceProviderUpdate', function(event, result) {
            vm.serviceProvider = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
