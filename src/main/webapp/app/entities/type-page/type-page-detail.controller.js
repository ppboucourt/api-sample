(function() {
    'use strict';

    angular
        .module('bluebookApp')
        .controller('TypePageDetailController', TypePageDetailController);

    TypePageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TypePage'];

    function TypePageDetailController($scope, $rootScope, $stateParams, previousState, entity, TypePage) {
        var vm = this;

        vm.typePage = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('bluebookApp:typePageUpdate', function(event, result) {
            vm.typePage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
