(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'Chart', 'LabCompendium', 'Actions', 'Medications'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, Chart, LabCompendium, Actions, Medications) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
