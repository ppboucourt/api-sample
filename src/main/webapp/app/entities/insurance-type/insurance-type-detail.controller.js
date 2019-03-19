(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceTypeDetailController', InsuranceTypeDetailController);

    InsuranceTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceType', 'Insurance', 'Corporation'];

    function InsuranceTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceType, Insurance, Corporation) {
        var vm = this;

        vm.insuranceType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:insuranceTypeUpdate', function(event, result) {
            vm.insuranceType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
