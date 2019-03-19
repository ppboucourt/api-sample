(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('UrgentIssuesDetailController', UrgentIssuesDetailController);

    UrgentIssuesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'UrgentIssues', 'Chart', 'Employee'];

    function UrgentIssuesDetailController($scope, $rootScope, $stateParams, previousState, entity, UrgentIssues, Chart, Employee) {
        var vm = this;

        vm.urgentIssues = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:urgentIssuesUpdate', function(event, result) {
            vm.urgentIssues = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
