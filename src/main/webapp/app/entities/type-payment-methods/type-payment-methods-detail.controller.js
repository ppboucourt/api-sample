(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePaymentMethodsDetailController', TypePaymentMethodsDetailController);

    TypePaymentMethodsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePaymentMethods'];

    function TypePaymentMethodsDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePaymentMethods) {
        var vm = this;

        vm.typePaymentMethods = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePaymentMethodsUpdate', function(event, result) {
            vm.typePaymentMethods = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
