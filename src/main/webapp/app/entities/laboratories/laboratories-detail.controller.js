(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('LaboratoriesDetailController', LaboratoriesDetailController);

    LaboratoriesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Laboratories'];

    function LaboratoriesDetailController($scope, $rootScope, $stateParams, previousState, entity, Laboratories) {
        var vm = this;

        vm.laboratories = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:laboratoriesUpdate', function(event, result) {
            vm.laboratories = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
