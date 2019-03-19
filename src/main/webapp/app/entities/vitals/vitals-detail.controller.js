(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('VitalsDetailController', VitalsDetailController);

    VitalsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Vitals', 'Chart'];

    function VitalsDetailController($scope, $rootScope, $stateParams, previousState, entity, Vitals, Chart) {
        var vm = this;

        vm.vitals = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:vitalsUpdate', function(event, result) {
            vm.vitals = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
