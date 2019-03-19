(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ActionsDetailController', ActionsDetailController);

    ActionsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Actions', 'ChartToAction', 'Orders'];

    function ActionsDetailController($scope, $rootScope, $stateParams, previousState, entity, Actions, ChartToAction, Orders) {
        var vm = this;

        vm.actions = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:actionsUpdate', function(event, result) {
            vm.actions = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
