(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderComponentDetailController', OrderComponentDetailController);

    OrderComponentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderComponent', 'AllOrders', 'Routes'];

    function OrderComponentDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderComponent, AllOrders, Routes) {
        var vm = this;

        vm.orderComponent = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:orderComponentUpdate', function(event, result) {
            vm.orderComponent = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
