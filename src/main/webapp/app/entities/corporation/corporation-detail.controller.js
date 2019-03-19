(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('CorporationDetailController', CorporationDetailController);

    CorporationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Corporation'];

    function CorporationDetailController($scope, $rootScope, $stateParams, previousState, entity, Corporation) {
        var vm = this;

        vm.corporation = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:corporationUpdate', function(event, result) {
            vm.corporation = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
