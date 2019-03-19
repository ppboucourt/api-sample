(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEthnicityDetailController', TypeEthnicityDetailController);

    TypeEthnicityDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeEthnicity'];

    function TypeEthnicityDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeEthnicity) {
        var vm = this;

        vm.typeEthnicity = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeEthnicityUpdate', function(event, result) {
            vm.typeEthnicity = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
