(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('RoutesDetailController', RoutesDetailController);

    RoutesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Routes'];

    function RoutesDetailController($scope, $rootScope, $stateParams, previousState, entity, Routes) {
        var vm = this;

        vm.routes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:routesUpdate', function(event, result) {
            vm.routes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
