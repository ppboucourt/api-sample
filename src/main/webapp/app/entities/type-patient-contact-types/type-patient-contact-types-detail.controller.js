(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactTypesDetailController', TypePatientContactTypesDetailController);

    TypePatientContactTypesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePatientContactTypes'];

    function TypePatientContactTypesDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePatientContactTypes) {
        var vm = this;

        vm.typePatientContactTypes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePatientContactTypesUpdate', function(event, result) {
            vm.typePatientContactTypes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
