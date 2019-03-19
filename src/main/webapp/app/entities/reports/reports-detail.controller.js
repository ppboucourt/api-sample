(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ReportsDetailController', ReportsDetailController);

    ReportsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Reports'];

    function ReportsDetailController($scope, $rootScope, $stateParams, previousState, entity, Reports) {
        var vm = this;

        vm.reports = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:reportsUpdate', function(event, result) {
            vm.reports = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
