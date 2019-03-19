(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceLevelDetailController', InsuranceLevelDetailController);

    InsuranceLevelDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceLevel', 'Insurance', 'Corporation'];

    function InsuranceLevelDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceLevel, Insurance, Corporation) {
        var vm = this;

        vm.insuranceLevel = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:insuranceLevelUpdate', function(event, result) {
            vm.insuranceLevel = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
