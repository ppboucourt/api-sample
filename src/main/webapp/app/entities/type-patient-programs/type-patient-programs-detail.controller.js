(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientProgramsDetailController', TypePatientProgramsDetailController);

    TypePatientProgramsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePatientPrograms', 'Chart', 'Facility'];

    function TypePatientProgramsDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePatientPrograms, Chart, Facility) {
        var vm = this;

        vm.typePatientPrograms = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePatientProgramsUpdate', function(event, result) {
            vm.typePatientPrograms = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
