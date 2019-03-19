(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePatientContactsRelationshipDetailController', TypePatientContactsRelationshipDetailController);

    TypePatientContactsRelationshipDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePatientContactsRelationship'];

    function TypePatientContactsRelationshipDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePatientContactsRelationship) {
        var vm = this;

        vm.typePatientContactsRelationship = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePatientContactsRelationshipUpdate', function(event, result) {
            vm.typePatientContactsRelationship = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
