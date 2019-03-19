(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypeDischargeTypeDetailController', TypeDischargeTypeDetailController);

    TypeDischargeTypeDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypeDischargeType'];

    function TypeDischargeTypeDetailController($scope, $rootScope, $stateParams, previousState, entity, TypeDischargeType) {
        var vm = this;

        vm.typeDischargeType = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typeDischargeTypeUpdate', function(event, result) {
            vm.typeDischargeType = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
