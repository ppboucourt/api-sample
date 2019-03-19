(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('DrugsDetailController', DrugsDetailController);

    DrugsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Drugs', 'Chart'];

    function DrugsDetailController($scope, $rootScope, $stateParams, previousState, entity, Drugs, Chart) {
        var vm = this;

        vm.drugs = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:drugsUpdate', function(event, result) {
            vm.drugs = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
