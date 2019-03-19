(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('ViaDetailController', ViaDetailController);

    ViaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Via', 'PatientOrder'];

    function ViaDetailController($scope, $rootScope, $stateParams, previousState, entity, Via, PatientOrder) {
        var vm = this;

        vm.via = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:viaUpdate', function(event, result) {
            vm.via = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
