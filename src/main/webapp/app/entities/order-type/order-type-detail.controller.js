(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('Order_typeDetailController', Order_typeDetailController);

    Order_typeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Order_type'];

    function Order_typeDetailController($scope, $rootScope, $stateParams, previousState, entity, Order_type) {
        var vm = this;

        vm.order_type = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:order_typeUpdate', function(event, result) {
            vm.order_type = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
