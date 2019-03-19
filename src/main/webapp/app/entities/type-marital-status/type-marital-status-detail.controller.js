(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeMaritalStatusDetailController', TypeMaritalStatusDetailController);

    TypeMaritalStatusDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeMaritalStatus'];

    function TypeMaritalStatusDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeMaritalStatus) {
        var vm = this;

        vm.typeMaritalStatus = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeMaritalStatusUpdate', function(event, result) {
            vm.typeMaritalStatus = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
