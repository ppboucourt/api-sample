(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceCarrierDetailController', InsuranceCarrierDetailController);

    InsuranceCarrierDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceCarrier', 'Insurance'];

    function InsuranceCarrierDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceCarrier, Insurance) {
        var vm = this;

        vm.insuranceCarrier = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:insuranceCarrierUpdate', function(event, result) {
            vm.insuranceCarrier = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
