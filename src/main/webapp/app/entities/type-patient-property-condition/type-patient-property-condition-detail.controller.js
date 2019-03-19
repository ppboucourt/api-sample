(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientPropertyConditionDetailController', TypePatientPropertyConditionDetailController);

    TypePatientPropertyConditionDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePatientPropertyCondition'];

    function TypePatientPropertyConditionDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePatientPropertyCondition) {
        var vm = this;

        vm.typePatientPropertyCondition = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePatientPropertyConditionUpdate', function(event, result) {
            vm.typePatientPropertyCondition = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
