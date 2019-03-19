(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('OrderFrequencyDetailController', OrderFrequencyDetailController);

    OrderFrequencyDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderFrequency', 'PatientOrderTest'];

    function OrderFrequencyDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderFrequency, PatientOrderTest) {
        var vm = this;

        vm.orderFrequency = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:orderFrequencyUpdate', function(event, result) {
            vm.orderFrequency = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
