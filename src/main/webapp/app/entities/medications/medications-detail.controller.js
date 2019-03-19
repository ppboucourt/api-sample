(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('MedicationsDetailController', MedicationsDetailController);

    MedicationsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medications', 'Orders'];

    function MedicationsDetailController($scope, $rootScope, $stateParams, previousState, entity, Medications, Orders) {
        var vm = this;

        vm.medications = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:medicationsUpdate', function(event, result) {
            vm.medications = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
