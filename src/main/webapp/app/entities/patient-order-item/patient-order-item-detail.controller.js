(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('PatientOrderItemDetailController', PatientOrderItemDetailController);

    PatientOrderItemDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'PatientOrderItem', 'PatientOrderTest'];

    function PatientOrderItemDetailController($scope, $rootScope, $stateParams, previousState, entity, PatientOrderItem, PatientOrderTest) {
        var vm = this;

        vm.patientOrderItem = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:patientOrderItemUpdate', function(event, result) {
            vm.patientOrderItem = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
