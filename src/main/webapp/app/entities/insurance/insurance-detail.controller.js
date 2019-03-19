(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceDetailController', InsuranceDetailController);

    InsuranceDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Insurance', 'InsuranceCarrier', 'InsuranceType', 'InsuranceLevel', 'InsuranceRelation', 'Patient', 'CountryState'];

    function InsuranceDetailController($scope, $rootScope, $stateParams, previousState, entity, Insurance, InsuranceCarrier, InsuranceType, InsuranceLevel, InsuranceRelation, Patient, CountryState) {
        var vm = this;

        vm.insurance = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:insuranceUpdate', function(event, result) {
            vm.insurance = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
