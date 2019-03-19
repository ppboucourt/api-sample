(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PayerDetailController', PayerDetailController);

    PayerDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Payer'];

    function PayerDetailController($scope, $rootScope, $stateParams, previousState, entity, Payer) {
        var vm = this;

        vm.payer = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:payerUpdate', function(event, result) {
            vm.payer = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
