(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('EmployeeDetailController', EmployeeDetailController);

    EmployeeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Employee', 'Shifts', 'UrgentIssues', 'Chart', 'User', 'Corporation'];

    function EmployeeDetailController($scope, $rootScope, $stateParams, previousState, entity, Employee, Shifts, UrgentIssues, Chart, User, Corporation) {
        var vm = this;

        vm.employee = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:employeeUpdate', function(event, result) {
            vm.employee = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
