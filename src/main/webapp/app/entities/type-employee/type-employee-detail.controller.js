(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeEmployeeDetailController', TypeEmployeeDetailController);

    TypeEmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeEmployee', 'Employee'];

    function TypeEmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeEmployee, Employee) {
        var vm = this;

        vm.typeEmployee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeEmployeeUpdate', function(event, result) {
            vm.typeEmployee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
