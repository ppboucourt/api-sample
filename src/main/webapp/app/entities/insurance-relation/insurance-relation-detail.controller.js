(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('InsuranceRelationDetailController', InsuranceRelationDetailController);

    InsuranceRelationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'InsuranceRelation', 'Insurance', 'Corporation'];

    function InsuranceRelationDetailController($scope, $rootScope, $stateParams, previousState, entity, InsuranceRelation, Insurance, Corporation) {
        var vm = this;

        vm.insuranceRelation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:insuranceRelationUpdate', function(event, result) {
            vm.insuranceRelation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
