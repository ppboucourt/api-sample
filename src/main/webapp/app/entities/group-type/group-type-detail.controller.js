(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('GroupTypeDetailController', GroupTypeDetailController);

    GroupTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'GroupType', 'GroupSession'];

    function GroupTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, GroupType, GroupSession) {
        var vm = this;

        vm.groupType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:groupTypeUpdate', function(event, result) {
            vm.groupType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
