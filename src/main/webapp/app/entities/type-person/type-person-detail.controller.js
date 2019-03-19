(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePersonDetailController', TypePersonDetailController);

    TypePersonDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePerson'];

    function TypePersonDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePerson) {
        var vm = this;

        vm.typePerson = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePersonUpdate', function(event, result) {
            vm.typePerson = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
