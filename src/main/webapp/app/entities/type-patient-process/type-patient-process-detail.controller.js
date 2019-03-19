(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProcessDetailController', TypePatientProcessDetailController);

    TypePatientProcessDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePatientProcess', 'TypePage'];

    function TypePatientProcessDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePatientProcess, TypePage) {
        var vm = this;

        vm.typePatientProcess = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePatientProcessUpdate', function(event, result) {
            vm.typePatientProcess = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
